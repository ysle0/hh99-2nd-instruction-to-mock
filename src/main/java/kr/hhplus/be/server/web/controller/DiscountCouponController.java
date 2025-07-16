package kr.hhplus.be.server.web.controller;

import kr.hhplus.be.server.web.dto.IssueInOrderOfArrivalReply;
import kr.hhplus.be.server.web.dto.IssueInOrderOfArrivalRequest;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discount-coupons")
public class DiscountCouponController {
    @PostMapping("/issue")
    public RequestEntity<IssueInOrderOfArrivalReply>
    issueInOrderOfArrivalReply(
            @RequestBody IssueInOrderOfArrivalRequest r
    ) {
        return null;
    }
}
