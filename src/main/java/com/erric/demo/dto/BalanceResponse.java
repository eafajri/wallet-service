package com.erric.demo.dto;

public class BalanceResponse {

    private Long userId;
    private Long balance;

    public BalanceResponse(Long userId, Long balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Long getUserId() { return userId; }
    public Long getBalance() { return balance; }
}