package kr.hhplus.be.server.discountCoupon.presentation.dto;

import kr.hhplus.be.server.discountCoupon.domain.misc.DiscountCouponMessage;
import kr.hhplus.be.server.shared.Messages;
import kr.hhplus.be.server.shared.api.ApiResponse;

public record IssueInOrderOfArrivalResponse(boolean isIssued) {
    public ApiResponse<IssueInOrderOfArrivalResponse> Ok() {
        return new ApiResponse<>(
                DiscountCouponMessage.COUPON_ISSUED_SUCCESS,
                Messages.OK,
                this);
    }

    public ApiResponse<IssueInOrderOfArrivalResponse> Fail() {
        return new ApiResponse<>(
                DiscountCouponMessage.COUPON_ISSUED_FAIL,
                Messages.NO,
                new IssueInOrderOfArrivalResponse(false));
    }
}
