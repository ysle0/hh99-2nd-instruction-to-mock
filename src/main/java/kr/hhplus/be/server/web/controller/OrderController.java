package kr.hhplus.be.server.web.controller;

import kr.hhplus.be.server.web.dto.OrderProductReply;
import kr.hhplus.be.server.web.dto.OrderProductRequest;
import kr.hhplus.be.server.web.dto.ShowTop5ProductsWithin3DaysReply;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @PostMapping("/")
    public OrderProductReply orderProduct(
            @RequestBody OrderProductRequest r
    ) {
        return null;
    }

    @GetMapping("/top-5-products-within-3-days")
    public ShowTop5ProductsWithin3DaysReply showTop5ProductsWithin3Days() {
        return null;
    }
}
