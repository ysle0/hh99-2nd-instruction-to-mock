package kr.hhplus.be.server.user.wallet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.user.wallet.domain.exception.InsufficientWalletBalanceException;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Wallet {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "balance", nullable = false)
    @Setter
    private int balance;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // === 비즈니스 로직 ===

    /**
     * 잔액이 충분한지 검증
     */
    public void validateBalance(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("차감 금액은 0보다 커야 합니다: " + amount);
        }

        if (this.balance < amount) {
            throw new InsufficientWalletBalanceException(amount);
        }
    }

    /**
     * 잔액 차감 (비즈니스 규칙 포함)
     */
    public void withdraw(int amount) {
        validateBalance(amount);
        this.balance -= amount;
    }

    /**
     * 잔액 충전
     */
    public void charge(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다: " + amount);
        }

        // WalletPolicy 적용 가능 (최대/최소 충전 금액 등)
        WalletPolicy.validateChargeAmount(amount);

        this.balance += amount;
    }

    /**
     * 현재 잔액으로 구매 가능한지 확인
     */
    public boolean canAfford(int amount) {
        return this.balance >= amount && amount > 0;
    }
}
