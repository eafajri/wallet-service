package com.erric.demo.controller;

import com.erric.demo.dto.BalanceResponse;
import com.erric.demo.dto.BaseResponse;
import com.erric.demo.dto.PaymentRequest;
import com.erric.demo.dto.TopUpRequest;
import com.erric.demo.entity.Transaction;
import com.erric.demo.service.WalletService;
import org.springframework.web.bind.annotation.*;
import com.erric.demo.dto.PaginatedResponse;
import com.erric.demo.dto.TransactionResponse;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/{userId}/topup")
    public Transaction topUp(
            @PathVariable Long userId,
            @Valid @RequestBody TopUpRequest request
    ) {
        return walletService.topUp(
                userId,
                request.getAmount(),
                request.getReferenceId()
        );
    }

    @PostMapping("/{userId}/pay")
    public Transaction pay(
            @PathVariable Long userId,
            @Valid @RequestBody PaymentRequest request
    ) {
        return walletService.pay(
                userId,
                request.getAmount(),
                request.getReferenceId()
        );
    }

    @GetMapping("/{userId}/balance")
    public BaseResponse<BalanceResponse> getBalance(@PathVariable Long userId) {
        return new BaseResponse<>(walletService.getBalance(userId));
    }

    @GetMapping("/{userId}/transactions")
    public BaseResponse<PaginatedResponse<TransactionResponse>> getHistory(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return new BaseResponse<>(
                walletService.getHistory(userId, pageable)
        );
    }
}