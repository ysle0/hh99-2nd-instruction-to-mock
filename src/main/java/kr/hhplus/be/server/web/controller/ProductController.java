package kr.hhplus.be.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.Messages;
import kr.hhplus.be.server.domain.product.ProductMessages;
import kr.hhplus.be.server.util.ProductDummyGen;
import kr.hhplus.be.server.util.RandomUtil;
import kr.hhplus.be.server.web.dto.ApiResponse;
import kr.hhplus.be.server.web.dto.DummyProduct;
import kr.hhplus.be.server.web.dto.ShowProductResponse;
import kr.hhplus.be.server.web.dto.ShowTopNProductsWithinMDaysResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@ResponseStatus(HttpStatus.OK)
@Tag(name = "product", description = "상품 API")
public class ProductController {
    @GetMapping("/{productID}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 1개를 조회하기", description = "product_id 는 UUID. uuid 예시: 6bf3c701-089f-4a6b-9563-94e6a317dbe8")
    public ApiResponse<ShowProductResponse> showProducts(
            @PathVariable UUID productID
    ) {
        final boolean failedRandomly = Math.random() < 0.5;
        if (failedRandomly) {
            return new ApiResponse<>(
                    ProductMessages.PRODUCT_QUERY_FAILED,
                    Messages.CODE_NO,
                    new ShowProductResponse(
                            productID,
                            "",
                            0,
                            0)
            );

        }

        return new ApiResponse<>(
                ProductMessages.PRODUCT_QUERY_SUCCESS,
                Messages.CODE_OK,
                new ShowProductResponse(
                        productID,
                        ProductDummyGen.genProductName(),
                        RandomUtil.Range(1000, 11000 + 1),
                        RandomUtil.Range(1000, 100 + 1)));
    }

    @GetMapping("/range")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "M 일간 최대 판매한 N 개의 상품들을 조회하기", description = "query parameter days-within, top 은 0보다 큰 정수.")
    public ApiResponse<ShowTopNProductsWithinMDaysResponse> showRangedProducts(
            @RequestParam(name = "days-within") int daysWithin,
            @RequestParam(name = "top") int top
    ) {
        var products = ProductDummyGen.genProductNames(top)
                .stream()
                .map(productName ->
                        new DummyProduct(
                                UUID.randomUUID(),
                                productName,
                                RandomUtil.Range(1_000, 2_000_000),
                                RandomUtil.Range(1_000, 10_000)
                        ))
                .collect(Collectors.toList());

        return new ApiResponse<>(
                ProductMessages.PRODUCT_RANGE_QUERY_SUCCESS,
                Messages.CODE_OK,
                new ShowTopNProductsWithinMDaysResponse(products));
    }
}
