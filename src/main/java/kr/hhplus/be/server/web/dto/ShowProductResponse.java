package kr.hhplus.be.server.web.dto;

import java.util.UUID;

public record ShowProductResponse(
        UUID productID,
        String name,
        int price,
        int quantityLeft
) {
}
