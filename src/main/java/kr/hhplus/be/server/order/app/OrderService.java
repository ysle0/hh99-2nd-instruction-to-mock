package kr.hhplus.be.server.order.app;

import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderRepository;
import kr.hhplus.be.server.order.domain.misc.OrderStatus;
import kr.hhplus.be.server.order.presentation.dto.OrderProductResponse;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.ProductRepository;
import kr.hhplus.be.server.product.domain.exception.InsufficientProductStockException;
import kr.hhplus.be.server.product.domain.exception.InvalidProductIdException;
import kr.hhplus.be.server.product.domain.exception.InvalidProductQuantityException;
import kr.hhplus.be.server.shared.exception.InvalidUserException;
import kr.hhplus.be.server.user.wallet.domain.Wallet;
import kr.hhplus.be.server.user.wallet.domain.WalletRepository;
import kr.hhplus.be.server.user.wallet.domain.exception.InsufficientWalletBalanceException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final WalletRepository walletRepo;
    private final ProductRepository productRepo;

    private long orderIdGen;

    public OrderService(
            OrderRepository orderRepo,
            WalletRepository walletRepo,
            ProductRepository productRepo
    ) {
        this.orderRepo = orderRepo;
        this.walletRepo = walletRepo;
        this.productRepo = productRepo;
    }

    public OrderProductResponse orderProduct(long userID, long productID, int quantity)
            throws InvalidUserException, InvalidProductIdException, InvalidProductQuantityException {

        // 1. 입력값 검증
        validateOrderQuantity(productID, quantity);

        // 2. 도메인 객체 조회
        Wallet wallet = findWalletByUserId(userID);
        Product product = findProductById(productID);

        // 3. 도메인 객체에게 비즈니스 로직 위임
        int totalPrice = product.calculateTotalPrice(quantity);
        product.decreaseStock(quantity);
        wallet.withdraw(totalPrice);

        // 4. 저장 (트랜잭션 경계 관리)
        productRepo.save(product);
        walletRepo.save(wallet);

        // 5. 주문 생성
        Order newOrder = createOrder();
        Order savedOrder = orderRepo.save(newOrder);

        return new OrderProductResponse(true, savedOrder);
    }

    // === 입력값 검증 헬퍼 메서드 ===
    
    private void validateOrderQuantity(long productID, int quantity) {
        if (quantity <= 0) {
            throw new InvalidProductQuantityException(productID, quantity);
        }
    }
    
    // === 도메인 객체 조회 헬퍼 메서드 ===
    
    private Wallet findWalletByUserId(long userID) {
        Optional<Wallet> foundWallet = walletRepo.findByUserId(userID);
        if (foundWallet.isEmpty()) {
            throw new InvalidUserException(userID);
        }
        return foundWallet.get();
    }
    
    private Product findProductById(long productID) {
        Optional<Product> foundProduct = productRepo.findByProductId(productID);
        if (foundProduct.isEmpty()) {
            throw new InvalidProductIdException(productID);
        }
        return foundProduct.get();
    }
    
    private Order createOrder() {
        return Order.builder()
                .id(orderIdGen++)
                .status(OrderStatus.PENDING)
                .build();
    }
}
