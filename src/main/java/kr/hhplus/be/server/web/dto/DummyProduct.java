package kr.hhplus.be.server.web.dto;

import java.util.UUID;

public record DummyProduct(
        UUID productID,
        String name,
        int price,
        int quantityLeft
) {
}
