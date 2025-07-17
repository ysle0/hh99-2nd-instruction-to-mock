package kr.hhplus.be.server.domain.wallet;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.user.User;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID userID;

    @Column(nullable = false)
    private int balance;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Wallet CopyFrom(Wallet src) {
        var w = new Wallet();
        w.id = src.id;
        w.userID = src.userID;
        w.balance = src.balance;
        w.createdAt = src.createdAt;

        return w;
    }

}

