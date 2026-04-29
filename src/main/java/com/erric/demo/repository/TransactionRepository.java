package com.erric.demo.repository;

import com.erric.demo.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // =========================
    // IDEMPOTENCY
    // =========================
    Optional<Transaction> findByReferenceId(String referenceId);

    boolean existsByReferenceId(String referenceId);

    // =========================
    // BASIC HISTORY (pagination)
    // =========================
    Page<Transaction> findByWalletId(Long walletId, Pageable pageable);

    // =========================
    // FILTER: by type
    // =========================
    Page<Transaction> findByWalletIdAndType(
            Long walletId,
            String type,
            Pageable pageable
    );

    // =========================
    // FILTER: by date range
    // =========================
    Page<Transaction> findByWalletIdAndCreatedAtBetween(
            Long walletId,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );

    // =========================
    // FILTER: type + date range
    // =========================
    Page<Transaction> findByWalletIdAndTypeAndCreatedAtBetween(
            Long walletId,
            String type,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
}