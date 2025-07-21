package kr.hhplus.be.server.web.dto;

import java.util.UUID;

public record ShowProductResponse(
        long productID,
        String name,
        int price,
        int quantityLeft
) {
}
