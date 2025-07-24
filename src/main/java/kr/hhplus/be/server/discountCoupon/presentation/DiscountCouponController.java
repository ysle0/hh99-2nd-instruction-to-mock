package kr.hhplus.be.server.discountCoupon.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.discountCoupon.app.DiscountCouponService;
import kr.hhplus.be.server.discountCoupon.presentation.dto.IssueInOrderOfArrivalRequest;
import kr.hhplus.be.server.discountCoupon.presentation.dto.IssueInOrderOfArrivalResponse;
import kr.hhplus.be.server.shared.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discount-coupons")
@Tag(name = "discount coupon", description = "할인 쿠폰 API")
class DiscountCouponController {
    DiscountCouponService discountCouponService;

    @PostMapping("/issue")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "선착순으로 할인 쿠폰 1개 발급하기", description = "할인 쿠폰은 선착순으로 실패할 수 도있음. 할인쿠폰의 종류는 2가지 '금액', '퍼센트' 가 존재.")
    ApiResponse<IssueInOrderOfArrivalResponse> issueInOrderOfArrivalReply(
            @RequestBody IssueInOrderOfArrivalRequest r) {

        IssueInOrderOfArrivalResponse resp = discountCouponService.issueInOrderArrival(r.userId());

        return resp.Ok();
    }
}
