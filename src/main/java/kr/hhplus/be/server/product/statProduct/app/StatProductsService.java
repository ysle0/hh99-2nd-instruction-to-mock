package kr.hhplus.be.server.product.statProduct.app;

import kr.hhplus.be.server.product.statProduct.domain.TopSoldProductsRepository;
import kr.hhplus.be.server.product.statProduct.presentation.dto.ShowTopSoldProductsWithinDatesResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatProductsService {
    private final TopSoldProductsRepository topSoldProductsRepo;

    public StatProductsService(
            TopSoldProductsRepository topSoldProductsRepo
    ) {
        this.topSoldProductsRepo = topSoldProductsRepo;
    }

    public ShowTopSoldProductsWithinDatesResponse showTopSoldProductsWithinDays(int toRank, int withinDays) {
        return new ShowTopSoldProductsWithinDatesResponse(List.of());
    }
}
