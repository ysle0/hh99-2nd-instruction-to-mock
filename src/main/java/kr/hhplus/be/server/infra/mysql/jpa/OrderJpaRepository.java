package kr.hhplus.be.server.infra.mysql.jpa;

import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository
        extends OrderRepository,
        JpaRepository<Order, Long> {
}
