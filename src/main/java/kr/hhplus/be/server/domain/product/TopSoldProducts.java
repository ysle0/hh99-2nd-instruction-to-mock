package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "top_sold_products_within_days")
public class TopSoldProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "rank", nullable = false)
    private int rank;

    @Column(name = "sold_quantity", nullable = false)
    private int soldQuantity;

    @Column(name = "days_period", nullable = false)
    private int days;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
