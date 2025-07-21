package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "top_sold_products")
@Getter
@Setter
@ToString
public class TopSoldProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false, unique = true)
    private Long productId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "rank", nullable = false)
    private int rank;

    @Column(name = "sold_quantity", nullable = false)
    private int soldQuantity;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
