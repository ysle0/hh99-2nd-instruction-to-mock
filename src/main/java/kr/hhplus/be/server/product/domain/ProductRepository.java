package kr.hhplus.be.server.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findByProductId(long productId);

    List<Product> findTopProducts(int withinDays);
}
