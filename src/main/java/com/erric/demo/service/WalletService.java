package com.erric.demo.service;

import com.erric.demo.dto.BalanceResponse;
import com.erric.demo.entity.Transaction;
import com.erric.demo.entity.Wallet;
import com.erric.demo.exception.BadRequestException;
import com.erric.demo.exception.InsufficientBalanceException;
import com.erric.demo.exception.NotFoundException;
import com.erric.demo.repository.TransactionRepository;
import com.erric.demo.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.erric.demo.dto.PaginatedResponse;
import com.erric.demo.dto.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository,
                         TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    // =========================
    // TOP UP
    // =========================
    @Transactional
    public Transaction topUp(Long userId, Long amount, String referenceId) {

        if (amount == null || amount <= 0) {
            throw new BadRequestException("Amount must be greater than 0");
        }

        // idempotency
        var existingTx = transactionRepository.findByReferenceId(referenceId);
        if (existingTx.isPresent()) {
            return existingTx.get();
        }

        // lock wallet
        Wallet wallet = walletRepository.findByUserIdForUpdate(userId)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        // update balance
        wallet.setBalance(wallet.getBalance() + amount);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);

        // create transaction
        Transaction tx = new Transaction();
        tx.setWalletId(wallet.getId());
        tx.setType("TOPUP");
        tx.setAmount(amount);
        tx.setStatus("SUCCESS");
        tx.setReferenceId(referenceId);

        return transactionRepository.save(tx);
    }

    // =========================
    // PAYMENT
    // =========================
    @Transactional
    public Transaction pay(Long userId, Long amount, String referenceId) {

        if (amount == null || amount <= 0) {
            throw new BadRequestException("Amount must be greater than 0");
        }

        // idempotency
        var existingTx = transactionRepository.findByReferenceId(referenceId);
        if (existingTx.isPresent()) {
            return existingTx.get();
        }

        // lock wallet
        Wallet wallet = walletRepository.findByUserIdForUpdate(userId)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        // check balance
        if (wallet.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // deduct balance
        wallet.setBalance(wallet.getBalance() - amount);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);

        // create transaction
        Transaction tx = new Transaction();
        tx.setWalletId(wallet.getId());
        tx.setType("PAYMENT");
        tx.setAmount(amount);
        tx.setStatus("SUCCESS");
        tx.setReferenceId(referenceId);

        return transactionRepository.save(tx);
    }

    // =========================
    // GET BALANCE
    // =========================
    @Transactional(readOnly = true)
    public BalanceResponse getBalance(Long userId) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        return new BalanceResponse(userId, wallet.getBalance());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<TransactionResponse> getHistory(Long userId, Pageable pageable) {

        // 1. get wallet
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        // 2. query transactions
        Page<Transaction> page = transactionRepository
                .findByWalletId(wallet.getId(), pageable);

        // 3. map ke DTO
        var items = page.getContent().stream()
                .map(tx -> new TransactionResponse(
                        tx.getId(),
                        tx.getType(),
                        tx.getAmount(),
                        tx.getStatus(),
                        tx.getReferenceId(),
                        tx.getCreatedAt()
                ))
                .toList();

        // 4. wrap response
        return new PaginatedResponse<>(
                items,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}