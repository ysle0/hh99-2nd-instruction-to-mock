package kr.hhplus.be.server.web.dto;

import java.util.UUID;

public record OrderProductRequest(UUID userID, UUID productID, int quantity) {
}
