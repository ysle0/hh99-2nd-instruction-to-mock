package kr.hhplus.be.server.order.app;

import kr.hhplus.be.server.order.infra.OrderJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderJpaRepository orderRepo;

    public OrderService(
            OrderJpaRepository or
    ) {
        this.orderRepo = or;
    }

    public kr.hhplus.be.server.order.presentation.dto.OrderProductResponse orderProduct(long userID, long productID, int quantity) {
        return null;
    }
}
