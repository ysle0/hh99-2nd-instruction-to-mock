package kr.hhplus.be.server.order.app;

import kr.hhplus.be.server.order.domain.OrderRepository;
import kr.hhplus.be.server.order.presentation.dto.OrderProductResponse;
import kr.hhplus.be.server.user.wallet.domain.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final WalletRepository walletRepo;

    public OrderService(
            OrderRepository orderRepo,
            WalletRepository walletRepo
    ) {
        this.orderRepo = orderRepo;
        this.walletRepo = walletRepo;
    }

    public OrderProductResponse orderProduct(long userID, long productID, int quantity) {
        return null;
    }
}
