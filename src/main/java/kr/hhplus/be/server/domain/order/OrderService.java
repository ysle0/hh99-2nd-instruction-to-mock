package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.web.dto.OrderProductRequest;
import kr.hhplus.be.server.web.dto.OrderProductResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepo;

    public OrderService(
            OrderRepository or
    ) {
        this.orderRepo = or;
    }

    OrderProductResponse orderProduct(OrderProductRequest r) {
        return null;
    }
}
