package kr.hhplus.be.server.user;

import jakarta.persistence.*;
import kr.hhplus.be.server.discountCoupon.domain.DiscountCoupon;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_to_discount_coupons")
@Getter
@Setter
@ToString
public class UserToDiscountCoupons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private DiscountCoupon discountCoupon;

    @Column(name = "owned_quantity", nullable = false)
    private int ownedQuantity;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
