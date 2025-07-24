package kr.hhplus.be.server.product.infra;

import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductFakeRepository implements ProductRepository {

    private final Map<Long, Product> store;

    public ProductFakeRepository(Product fakeProduct) {
        this.store = new HashMap<>(fakeProduct.getQuantity());
        store.put(fakeProduct.getId(), fakeProduct);
    }

    @Override
    public Optional<Product> findByProductId(long productId) {
        if (!store.containsKey(productId)) {
            return Optional.empty();
        }
        Product found = store.get(productId);
        return Optional.of(found);
    }

    @Override
    public List<Product> findTopProducts(int withinDays) {
        return List.of();
    }
}
