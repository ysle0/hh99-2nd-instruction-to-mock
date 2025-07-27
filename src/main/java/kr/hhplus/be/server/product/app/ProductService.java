package kr.hhplus.be.server.product.app;

import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.ProductRepository;
import kr.hhplus.be.server.product.domain.exception.InvalidProductIdException;
import kr.hhplus.be.server.product.presentation.dto.ShowProductResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository prdDbRepo;

    public ProductService(
            @Qualifier("productJpaRepository") ProductRepository prdDbRepo
    ) {
        this.prdDbRepo = prdDbRepo;
    }

    public ShowProductResponse showProduct(long productId) {
        // NOTE: 이후 read cache 처리 필요
        // 1. 도메인 객체 조회
        Product product = findProductById(productId);

        return new ShowProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity()
        );
    }
    
    // === 도메인 객체 조회 헬퍼 메서드 ===
    
    private Product findProductById(long productId) {
        Optional<Product> foundProduct = prdDbRepo.findByProductId(productId);
        if (foundProduct.isEmpty()) {
            throw new InvalidProductIdException(productId);
        }
        return foundProduct.get();
    }

}
