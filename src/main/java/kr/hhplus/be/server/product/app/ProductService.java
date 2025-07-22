package kr.hhplus.be.server.product.app;

import kr.hhplus.be.server.product.domain.ProductRepository;
import kr.hhplus.be.server.product.presentation.dto.ShowProductResponse;
import kr.hhplus.be.server.product.presentation.dto.ShowTopProductsWithinDatesResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final ProductRepository productRedisRepo;

    public ProductService(
            @Qualifier("productJpaRepository") ProductRepository productJpaRepo,
            @Qualifier("productRedisRepository") ProductRepository productRedisRepo
    ) {
        this.productRepo = productJpaRepo;

        this.productRedisRepo = productRedisRepo;
    }

    public ShowProductResponse showProduct(long productId) {
        return null;
    }

    public ShowTopProductsWithinDatesResponse showTopProductsWithinDates(int daysWithin, int top) {
        return null;
    }

}
