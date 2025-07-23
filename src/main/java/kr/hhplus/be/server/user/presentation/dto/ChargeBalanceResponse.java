package kr.hhplus.be.server.user.presentation.dto;

import kr.hhplus.be.server.shared.Messages;
import kr.hhplus.be.server.shared.api.ApiResponse;
import kr.hhplus.be.server.user.domain.misc.WalletMessages;

public record ChargeBalanceResponse(boolean isCharged, int balanceAfterCharged) {
    public ApiResponse<ChargeBalanceResponse> Ok() {
        return new ApiResponse<>(
                WalletMessages.CHARGE_SUCCESS,
                Messages.OK,
                this);
    }
}
