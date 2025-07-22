package kr.hhplus.be.server.product.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.product.app.ProductService;
import kr.hhplus.be.server.product.presentation.dto.ShowProductResponse;
import kr.hhplus.be.server.product.presentation.dto.ShowTopProductsWithinDatesResponse;
import kr.hhplus.be.server.shared.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@ResponseStatus(HttpStatus.OK)
@Tag(name = "product", description = "상품 API")
public class ProductController {

    ProductService productService;

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 1개를 조회하기", description = "product_id 는 UUID. uuid 예시: 6bf3c701-089f-4a6b-9563-94e6a317dbe8")
    public ApiResponse<ShowProductResponse> showProducts(
            @PathVariable Long productId
    ) {
        ShowProductResponse resp = productService.showProduct(productId);

        return resp.Ok();
    }

    @GetMapping("/range")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "M 일간 최대 판매한 N 개의 상품들을 조회하기", description = "query parameter days-within, top 은 0보다 큰 정수.")
    public ApiResponse<ShowTopProductsWithinDatesResponse> showTopProductsWithinDates(
            @RequestParam(name = "days-within") int daysWithin,
            @RequestParam(name = "top") int top
    ) {
        ShowTopProductsWithinDatesResponse resp = productService.showTopProductsWithinDates(daysWithin, top);

        return resp.Ok();
    }
}
