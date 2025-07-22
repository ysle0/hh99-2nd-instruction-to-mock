package kr.hhplus.be.server.product.presentation.dto;

import kr.hhplus.be.server.product.domain.ProductMessages;
import kr.hhplus.be.server.shared.Messages;
import kr.hhplus.be.server.shared.api.ApiResponse;

public record ShowProductResponse(
        long productID,
        String name,
        int price,
        int quantityLeft
) {
    public ApiResponse<ShowProductResponse> Ok() {
        return new ApiResponse<>(
                ProductMessages.PRODUCT_QUERY_SUCCESS,
                Messages.OK,
                this);
    }
}
