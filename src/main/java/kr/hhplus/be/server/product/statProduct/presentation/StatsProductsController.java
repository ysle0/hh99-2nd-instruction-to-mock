package kr.hhplus.be.server.product.statProduct.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.product.statProduct.app.StatProductsService;
import kr.hhplus.be.server.product.statProduct.presentation.dto.ShowTopSoldProductsWithinDatesResponse;
import kr.hhplus.be.server.shared.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products/stats")
@ResponseStatus(HttpStatus.OK)
@Tag(name = "product", description = "상품 API")
public class StatsProductsController {

    StatProductsService statProductsService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "M 일간 최대 판매한 N 개의 상품들을 조회하기", description = "query parameter days-within, top 은 0보다 큰 정수.")
    public ApiResponse<ShowTopSoldProductsWithinDatesResponse> showTopProductsWithinDates(
            @RequestParam(name = "days-within") int daysWithin,
            @RequestParam(name = "to-rank") int toRank
    ) {
        ShowTopSoldProductsWithinDatesResponse resp = statProductsService.showTopSoldProductsWithinDays(toRank, daysWithin);

        return resp.Ok();
    }
}
