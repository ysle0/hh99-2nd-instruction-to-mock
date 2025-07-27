package kr.hhplus.be.server.discountCoupon.infra;

import kr.hhplus.be.server.discountCoupon.domain.DiscountCoupon;
import kr.hhplus.be.server.discountCoupon.domain.DiscountCouponRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("discountCouponJpaRepository")
public interface DiscountCouponJpaRepository extends DiscountCouponRepository, JpaRepository<DiscountCoupon, Long> {
}
