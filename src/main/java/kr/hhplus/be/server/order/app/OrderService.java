package kr.hhplus.be.server.order.app;

import kr.hhplus.be.server.order.domain.OrderRepository;
import kr.hhplus.be.server.order.presentation.dto.OrderProductResponse;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.ProductRepository;
import kr.hhplus.be.server.product.domain.exception.InsufficientProductStockException;
import kr.hhplus.be.server.product.domain.exception.InvalidProductIdException;
import kr.hhplus.be.server.product.domain.exception.InvalidProductQuantityException;
import kr.hhplus.be.server.shared.exception.InvalidUserException;
import kr.hhplus.be.server.user.wallet.domain.Wallet;
import kr.hhplus.be.server.user.wallet.domain.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final WalletRepository walletRepo;
    private final ProductRepository productRepo;

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
            throws InvalidUserException, InvalidProductIdException {

        if (quantity <= 0) {
            throw new InvalidProductQuantityException(productID, quantity);
        }

        Optional<Wallet> foundWallet = walletRepo.findByUserId(userID);
        if (foundWallet.isEmpty()) {
            throw new InvalidUserException(userID);
        }

        Optional<Product> foundProduct = productRepo.findByProductId(productID);
        if (foundProduct.isEmpty()) {
            throw new InvalidProductIdException(productID);
        }

        Product foundProductUnwrap = foundProduct.get();
        final int foundProductQuantity = foundProductUnwrap.getQuantity();
        final int quantityDiff = foundProductQuantity - quantity;
        if (quantityDiff < 0) {
            throw new InsufficientProductStockException(productID, quantity);
        }

        return null;
    }
}
