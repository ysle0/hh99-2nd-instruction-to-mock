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
        Optional<Product> found = prdDbRepo.findByProductId(productId);
        if (found.isEmpty()) {
            throw new InvalidProductIdException(productId);
        }

        Product foundUnwrap = found.get();

        return new ShowProductResponse(
                foundUnwrap.getId(),
                foundUnwrap.getName(),
                foundUnwrap.getPrice(),
                foundUnwrap.getQuantity()
        );
    }

}
