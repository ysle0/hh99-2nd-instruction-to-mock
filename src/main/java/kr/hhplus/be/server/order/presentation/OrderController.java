package kr.hhplus.be.server.order.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.order.app.OrderService;
import kr.hhplus.be.server.order.presentation.dto.OrderProductRequest;
import kr.hhplus.be.server.order.presentation.dto.OrderProductResponse;
import kr.hhplus.be.server.shared.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "order", description = "주문 API")
class OrderController {

    private OrderService orderService;


    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 1종류를 1~N개 주문하기")
    ApiResponse<OrderProductResponse> orderProduct(
            @RequestBody OrderProductRequest r) {
        if (r.quantity() <= 0) {
            return OrderProductResponse.InvalidQuantity();
        }

        OrderProductResponse resp = orderService.orderProduct(
                r.userID(), r.productID(), r.quantity());

        return resp.Ok();
    }
}
