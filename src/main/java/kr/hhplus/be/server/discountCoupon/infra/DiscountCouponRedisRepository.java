package kr.hhplus.be.server.discountCoupon.infra;

import kr.hhplus.be.server.discountCoupon.domain.DiscountCouponRepository;
import org.springframework.stereotype.Repository;

@Repository("discountCouponRedisRepository")
public class DiscountCouponRedisRepository implements DiscountCouponRepository {
}
