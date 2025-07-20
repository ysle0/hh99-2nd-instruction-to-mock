package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.discountCoupon.DiscountCoupon;
import kr.hhplus.be.server.domain.discountCoupon.DiscountCouponType;
import kr.hhplus.be.server.domain.user.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "discount_coupon_id")
    private DiscountCoupon discountCoupon;

    private int discountValueAtPurchase;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private DiscountCouponType discountCouponTypeAtPurchase;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order")
    private List<OrderToProduct> orderToProducts;
}
