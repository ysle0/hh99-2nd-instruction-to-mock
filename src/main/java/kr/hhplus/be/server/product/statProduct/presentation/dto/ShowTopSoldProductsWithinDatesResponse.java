package kr.hhplus.be.server.product.statProduct.presentation.dto;

import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.misc.ProductMessages;
import kr.hhplus.be.server.shared.Messages;
import kr.hhplus.be.server.shared.api.ApiResponse;

import java.util.List;

public record ShowTopSoldProductsWithinDatesResponse(List<Product> products) {
    public ApiResponse<ShowTopSoldProductsWithinDatesResponse> Ok() {
        return new ApiResponse<>(
                ProductMessages.PRODUCT_RANGE_QUERY_SUCCESS,
                Messages.OK,
                this);
    }
}

