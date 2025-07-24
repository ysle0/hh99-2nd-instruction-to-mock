package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.product.app.ProductService;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.ProductRepository;
import kr.hhplus.be.server.product.domain.exception.InvalidProductIdException;
import kr.hhplus.be.server.product.presentation.dto.ShowProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("상품 기능 유닛 테스트")
@ExtendWith(MockitoExtension.class)
public class ProductUnitTest {
    private static final long USER_ID = 123L;
    private static final long PRODUCT_ID = 2L;

    @DisplayName("상품 조회 기능 테스팅")
    @Nested
    class ShowTest {
        @DisplayName("없는 상품 조회시 InvalidProductIdException 을 던짐.")
        @Test
        public void shouldThrowInvalidProductIdException() {

            var repo = Mockito.mock(ProductRepository.class);
            var svc = new ProductService(repo);

            var ex = assertThrows(
                    InvalidProductIdException.class,
                    () -> svc.showProduct(PRODUCT_ID));

            assert ex.getMessage().equals(InvalidProductIdException.MakeMessage(PRODUCT_ID));
        }

        @DisplayName("정상 productId 는 정상 상품을 조회.")
        @Test
        public void shouldShowValidProductWithValidProductId() {

            var expect = Product.builder()
                    .id(PRODUCT_ID)
                    .price(100)
                    .name("바나나")
                    .quantity(25)
                    .build();

            var repo = Mockito.mock(ProductRepository.class);
            when(repo.findByProductId(PRODUCT_ID)).thenReturn(Optional.of(expect));
            var svc = new ProductService(repo);

            ShowProductResponse resp = svc.showProduct(PRODUCT_ID);

            assert resp.name().equals(expect.getName());
            assert resp.price() == expect.getPrice();
            assert resp.productID() == expect.getId();
            assert resp.quantityLeft() == expect.getQuantity();
        }


    }
}
