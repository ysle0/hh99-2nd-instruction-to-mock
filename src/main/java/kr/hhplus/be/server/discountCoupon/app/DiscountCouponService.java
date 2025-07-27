package kr.hhplus.be.server.discountCoupon.app;

import kr.hhplus.be.server.discountCoupon.domain.DiscountCouponRepository;
import kr.hhplus.be.server.discountCoupon.presentation.dto.IssueInOrderOfArrivalResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DiscountCouponService {
    private final DiscountCouponRepository discountCouponRepo;
    private final DiscountCouponRepository discountCouponCacheRepo;

    public DiscountCouponService(
            @Qualifier("discountCouponJpaRepository") DiscountCouponRepository dcDbRepo,
            @Qualifier("discountCouponRedisRepository") DiscountCouponRepository dcCacheRepo
    ) {
        this.discountCouponRepo = dcDbRepo;
        this.discountCouponCacheRepo = dcCacheRepo;
    }

    public IssueInOrderOfArrivalResponse issueInOrderArrival(long userId) {
        // 선착순 쿠폰 발급
        return null;
    }
}
