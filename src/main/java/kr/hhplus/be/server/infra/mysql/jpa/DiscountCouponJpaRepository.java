package kr.hhplus.be.server.infra.mysql.jpa;

import kr.hhplus.be.server.domain.discountCoupon.DiscountCoupon;
import kr.hhplus.be.server.domain.discountCoupon.DiscountCouponRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCouponJpaRepository extends
        DiscountCouponRepository,
        JpaRepository<DiscountCoupon, Long> {
}
