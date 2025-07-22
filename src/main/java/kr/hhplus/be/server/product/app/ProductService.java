package kr.hhplus.be.server.product.app;

import kr.hhplus.be.server.product.infra.ProductJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJpaRepository productRepo;

    public ProductService(
            ProductJpaRepository pr
    ) {
        this.productRepo = pr;
    }

    public kr.hhplus.be.server.product.presentation.dto.ShowProductResponse showProduct(long productId) {
        return null;
    }

    public kr.hhplus.be.server.product.presentation.dto.ShowTopProductsWithinDatesResponse showTopProductsWithinDates(int daysWithin, int top) {
        return null;
    }

}
