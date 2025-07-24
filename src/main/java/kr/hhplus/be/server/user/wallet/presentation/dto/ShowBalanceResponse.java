package kr.hhplus.be.server.user.wallet.presentation.dto;

import kr.hhplus.be.server.shared.Messages;
import kr.hhplus.be.server.shared.api.ApiResponse;
import kr.hhplus.be.server.user.wallet.domain.WalletMessages;

public record ShowBalanceResponse(int balance) {
    public ApiResponse<ShowBalanceResponse> Ok() {
        return new ApiResponse<>(
                WalletMessages.BALANCE_QUERY_SUCCESS,
                Messages.OK,
                this);
    }
}
