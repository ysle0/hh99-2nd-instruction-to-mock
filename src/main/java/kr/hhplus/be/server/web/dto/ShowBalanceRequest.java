package kr.hhplus.be.server.web.dto;

import java.util.UUID;

public record ShowBalanceRequest(
        UUID userID
) {
}
