package kr.hhplus.be.server.infra.mysql.jpa;

import kr.hhplus.be.server.domain.product.ProductRepository;
import kr.hhplus.be.server.domain.product.ProductService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository
        extends ProductRepository,
        JpaRepository<ProductService, Long> {
}
