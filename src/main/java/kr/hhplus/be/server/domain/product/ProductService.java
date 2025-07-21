package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.web.dto.ShowProductResponse;
import kr.hhplus.be.server.web.dto.ShowTopProductsWithinDates;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(
            ProductRepository pr
    ) {
        this.productRepo = pr;
    }

    ShowProductResponse showProduct(long productId) {
        return null;
    }

    ShowTopProductsWithinDates showTopProductsWithinDates(int daysWithin, int top) {
        return null;
    }

}
