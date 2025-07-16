package kr.hhplus.be.server.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public record User(
        @Id UUID id,
        @CreatedDate LocalDateTime createdAt
) {
}
