package kr.hhplus.be.server.order.presentation.dto;

public record OrderProductRequest(Long userID, Long productID, int quantity) {
}
