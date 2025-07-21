package kr.hhplus.be.server.domain.wallet;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@ToString
public class Wallet {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "balance", nullable = false)
    private int balance;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
