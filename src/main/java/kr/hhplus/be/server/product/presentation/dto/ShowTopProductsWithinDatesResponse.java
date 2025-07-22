package kr.hhplus.be.server.product.presentation.dto;

import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.ProductMessages;
import kr.hhplus.be.server.shared.Messages;
import kr.hhplus.be.server.shared.api.ApiResponse;

import java.util.List;

public record ShowTopProductsWithinDatesResponse(List<Product> products) {
    public ApiResponse<ShowTopProductsWithinDatesResponse> Ok() {
        return new ApiResponse<>(
                ProductMessages.PRODUCT_RANGE_QUERY_SUCCESS,
                Messages.OK,
                this);
    }
}

