package kr.hhplus.be.server.discountCoupon.app;

import kr.hhplus.be.server.discountCoupon.infra.DiscountCouponJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DiscountCouponService {
    private final DiscountCouponJpaRepository discountCouponRepo;

    public DiscountCouponService(DiscountCouponJpaRepository dcr
    ) {
        this.discountCouponRepo = dcr;
    }

    public kr.hhplus.be.server.discountCoupon.presentation.dto.IssueInOrderOfArrivalResponse issueInOrderArrival(long userId) {
        // 선착순 쿠폰 발급
        return null;
    }
}
