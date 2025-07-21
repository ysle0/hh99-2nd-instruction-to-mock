package kr.hhplus.be.server.domain.discountCoupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.user.UserToDiscountCoupons;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "discount_coupons")
public class DiscountCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiscountCouponType type;

    @Column(name = "discount_value")
    private int discountValue;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @OneToMany(mappedBy = "discountCoupon")
    private List<UserToDiscountCoupons> usersToDiscountedCoupons;
}
