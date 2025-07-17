package kr.hhplus.be.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.util.RandomUtil;
import kr.hhplus.be.server.web.dto.IssueInOrderOfArrivalReply;
import kr.hhplus.be.server.web.dto.IssueInOrderOfArrivalRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discount-coupons")
@Tag(name = "discount coupon", description = "할인 쿠폰 API")
public class DiscountCouponController {
    @PostMapping("/issue")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "선착순으로 할인 쿠폰 1개 발급하기", description = "할인 쿠폰은 선착순으로 실패할 수 도있음. 할인쿠폰의 종류는 2가지 '금액', '퍼센트' 가 존재.")
    public IssueInOrderOfArrivalReply issueInOrderOfArrivalReply(
            @RequestBody IssueInOrderOfArrivalRequest r) {

        return new IssueInOrderOfArrivalReply(
                RandomUtil.Bool() // Simulating the issuance of a discount coupon randomly
        );
    }
}
