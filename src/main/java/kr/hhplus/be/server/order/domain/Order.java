package kr.hhplus.be.server.order.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.discountCoupon.domain.DiscountCoupon;
import kr.hhplus.be.server.discountCoupon.domain.misc.DiscountCouponType;
import kr.hhplus.be.server.order.domain.misc.OrderStatus;
import kr.hhplus.be.server.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@ToString
@Builder
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @ToString.Exclude
    private List<OrderToProduct> orderToProducts;
}
