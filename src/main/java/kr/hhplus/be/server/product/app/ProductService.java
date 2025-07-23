package kr.hhplus.be.server.product.app;

import kr.hhplus.be.server.product.domain.ProductRepository;
import kr.hhplus.be.server.product.presentation.dto.ShowProductResponse;
import kr.hhplus.be.server.product.presentation.dto.ShowTopProductsWithinDatesResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository prdDbRepo;

    public ProductService(
            @Qualifier("productJpaRepository") ProductRepository prdDbRepo
    ) {
        this.prdDbRepo = prdDbRepo;
    }

    public ShowProductResponse showProduct(long productId) {
    }

    }

}
