package kr.hhplus.be.server.product.infra;

import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productJpaRepository")
public interface ProductJpaRepository extends ProductRepository, JpaRepository<Product, Long> {
}
