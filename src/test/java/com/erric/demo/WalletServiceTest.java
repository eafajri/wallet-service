package com.erric.demo;

import com.erric.demo.entity.Transaction;
import com.erric.demo.entity.Wallet;
import com.erric.demo.exception.InsufficientBalanceException;
import com.erric.demo.repository.TransactionRepository;
import com.erric.demo.repository.WalletRepository;
import com.erric.demo.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private WalletService walletService;

    // =========================
    // TEST: TOPUP SUCCESS
    // =========================
    @Test
    void topUp_success() {

        Wallet wallet = new Wallet();
        wallet.setUserId(1L);
        wallet.setBalance(10000L);

        when(transactionRepository.findByReferenceId("trx-1"))
                .thenReturn(Optional.empty());

        when(walletRepository.findByUserIdForUpdate(1L))
                .thenReturn(Optional.of(wallet));

        when(transactionRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = walletService.topUp(1L, 5000L, "trx-1");

        assertEquals(15000L, wallet.getBalance());
        assertEquals("TOPUP", result.getType());

        verify(walletRepository).save(wallet);
        verify(transactionRepository).save(any());
    }

    // =========================
    // TEST: IDEMPOTENCY
    // =========================
    @Test
    void topUp_idempotent_shouldReturnExisting() {

        Transaction existing = new Transaction();
        existing.setReferenceId("trx-1");

        when(transactionRepository.findByReferenceId("trx-1"))
                .thenReturn(Optional.of(existing));

        Transaction result = walletService.topUp(1L, 5000L, "trx-1");

        assertEquals("trx-1", result.getReferenceId());

        verify(walletRepository, never()).save(any());
    }

    // =========================
    // TEST: PAYMENT INSUFFICIENT BALANCE
    // =========================
    @Test
    void pay_shouldThrowIfInsufficientBalance() {

        Wallet wallet = new Wallet();
        wallet.setUserId(1L);
        wallet.setBalance(1000L);

        when(transactionRepository.findByReferenceId("pay-1"))
                .thenReturn(Optional.empty());

        when(walletRepository.findByUserIdForUpdate(1L))
                .thenReturn(Optional.of(wallet));

        assertThrows(InsufficientBalanceException.class, () ->
                walletService.pay(1L, 5000L, "pay-1")
        );

        verify(walletRepository, never()).save(any());
    }
}