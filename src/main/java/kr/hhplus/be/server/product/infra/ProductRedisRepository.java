package kr.hhplus.be.server.product.infra;

import kr.hhplus.be.server.product.domain.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository("productRedisRepository")
public class ProductRedisRepository implements ProductRepository {
}
