package kr.hhplus.be.server.domain.wallet;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "balance", nullable = false)
    private int balance;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
