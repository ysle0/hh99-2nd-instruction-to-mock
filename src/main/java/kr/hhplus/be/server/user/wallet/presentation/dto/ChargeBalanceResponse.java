package kr.hhplus.be.server.user.wallet.presentation.dto;

import kr.hhplus.be.server.shared.Messages;
import kr.hhplus.be.server.shared.api.ApiResponse;
import kr.hhplus.be.server.user.wallet.domain.WalletMessages;

public record ChargeBalanceResponse(boolean isCharged) {
    public ApiResponse<ChargeBalanceResponse> Ok() {
        return new ApiResponse<>(
                WalletMessages.CHARGE_SUCCESS,
                Messages.OK,
                this);
    }
}
