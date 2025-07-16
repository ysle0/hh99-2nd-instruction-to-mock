package kr.hhplus.be.server.infra.discountCoupon;

import kr.hhplus.be.server.domain.discountCoupon.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, String> {
}
