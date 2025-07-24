package kr.hhplus.be.server.order.infra;

import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderRepository;

import java.util.HashMap;
import java.util.Map;

public class OrderFakeRepository implements OrderRepository {
    final Map<Long, Order> store;

    public OrderFakeRepository() {
        store = new HashMap<>(1);
    }

    @Override
    public Order save(Order order) {
        store.put(order.getId(), order);
        return order;
    }
}
