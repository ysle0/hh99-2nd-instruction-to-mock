package kr.hhplus.be.server.user.wallet.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ChargeBalanceRequest(
        @NotBlank(message = "userID is required.")
        Long userID,

        @NotBlank(message = "balance must be positive number.")
        @Positive()
        int newBalance
) {
}
