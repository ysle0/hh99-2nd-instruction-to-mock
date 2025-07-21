package kr.hhplus.be.server.domain.user;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.discountCoupon.DiscountCoupon;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_to_discount_coupons")
public class UserToDiscountCoupons {
    @EmbeddedId
    private Long id;

    @ManyToOne
    @MapsId("user_id")
    @Column(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("discount_coupon_id")
    @Column(name = "discount_coupon_id", nullable = false)
    private DiscountCoupon discountCoupon;

    @Column(name = "owned_quantity", nullable = false)
    private long ownedQuantity;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
