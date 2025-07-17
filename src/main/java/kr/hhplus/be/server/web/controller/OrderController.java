package kr.hhplus.be.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.web.dto.OrderProductReply;
import kr.hhplus.be.server.web.dto.OrderProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "order", description = "주문 API")
public class OrderController {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 1종류를 1~N개 주문하기")
    public OrderProductReply orderProduct(
            @RequestBody OrderProductRequest r) {
        if (r.quantity() <= 0) {
            return new OrderProductReply(false);
        }

        return new OrderProductReply(true);
    }
}
