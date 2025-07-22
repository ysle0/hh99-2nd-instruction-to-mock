package kr.hhplus.be.server.discountCoupon.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.discountCoupon.domain.misc.DiscountCouponType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "discount_coupons")
@ToString
@Getter
@Setter
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
}
