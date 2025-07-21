package kr.hhplus.be.server.web.dto;

public record OrderProductRequest(Long userID, Long productID, int quantity) {
}
