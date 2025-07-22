package kr.hhplus.be.server.order.presentation.dto;

import kr.hhplus.be.server.order.domain.OrderMessages;
import kr.hhplus.be.server.shared.Messages;
import kr.hhplus.be.server.shared.api.ApiResponse;

public record OrderProductResponse(boolean isOrdered) {
    public ApiResponse<OrderProductResponse> Ok() {
        return new ApiResponse<>(
                OrderMessages.ORDER_SUCCESS,
                Messages.OK,
                this);
    }

    public static ApiResponse<OrderProductResponse> InvalidQuantity() {
        return new ApiResponse<>(
                OrderMessages.ORDER_INVALID_QUANTITY,
                Messages.NO,
                new OrderProductResponse(false));
    }

}
