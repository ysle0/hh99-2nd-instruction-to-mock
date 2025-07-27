package kr.hhplus.be.server.product.domain;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findByProductId(long productId);

    Product save(Product newProduct);
}
