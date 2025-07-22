package kr.hhplus.be.server.discountCoupon.infra;

import kr.hhplus.be.server.discountCoupon.domain.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCouponJpaRepository extends JpaRepository<DiscountCoupon, Long> {
}
