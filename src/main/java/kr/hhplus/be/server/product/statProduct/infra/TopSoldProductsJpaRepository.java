package kr.hhplus.be.server.product.statProduct.infra;


import kr.hhplus.be.server.product.statProduct.domain.TopSoldProducts;
import kr.hhplus.be.server.product.statProduct.domain.TopSoldProductsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopSoldProductsJpaRepository extends TopSoldProductsRepository, JpaRepository<TopSoldProducts, Long> {
}
