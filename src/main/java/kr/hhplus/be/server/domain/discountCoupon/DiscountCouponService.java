package kr.hhplus.be.server.domain.discountCoupon;

import kr.hhplus.be.server.web.dto.IssueInOrderOfArrivalRequest;
import kr.hhplus.be.server.web.dto.IssueInOrderOfArrivalResponse;
import org.springframework.stereotype.Service;

@Service
public class DiscountCouponService {
    private final DiscountCouponRepository discountCouponRepo;

    public DiscountCouponService(
            DiscountCouponRepository dcr
    ) {
        this.discountCouponRepo = dcr;
    }

    IssueInOrderOfArrivalResponse issueInOrderArrival(IssueInOrderOfArrivalRequest r) {
        return null;
    }
}
