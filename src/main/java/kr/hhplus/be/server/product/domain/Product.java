package kr.hhplus.be.server.product.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.product.domain.exception.InsufficientProductStockException;
import kr.hhplus.be.server.product.domain.exception.InvalidProductQuantityException;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Builder(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    @Setter
    private int quantity;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // === 비즈니스 로직 ===
    
    /**
     * 요청 수량이 유효한지 검증
     */
    public void validateRequestQuantity(int requestQuantity) {
        if (requestQuantity <= 0) {
            throw new InvalidProductQuantityException(this.id, requestQuantity);
        }
    }
    
    /**
     * 재고가 충분한지 검증
     */
    public void validateStock(int requestQuantity) {
        validateRequestQuantity(requestQuantity);
        
        if (this.quantity < requestQuantity) {
            throw new InsufficientProductStockException(this.id, requestQuantity);
        }
    }
    
    /**
     * 재고 차감
     */
    public void decreaseStock(int requestQuantity) {
        validateStock(requestQuantity);
        this.quantity -= requestQuantity;
    }
    
    /**
     * 총 가격 계산
     */
    public int calculateTotalPrice(int requestQuantity) {
        validateRequestQuantity(requestQuantity);
        return this.price * requestQuantity;
    }
}
