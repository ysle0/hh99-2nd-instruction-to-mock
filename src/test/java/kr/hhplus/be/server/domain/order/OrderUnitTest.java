package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.order.app.OrderService;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderRepository;
import kr.hhplus.be.server.order.presentation.dto.OrderProductResponse;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.ProductRepository;
import kr.hhplus.be.server.product.domain.exception.InsufficientProductStockException;
import kr.hhplus.be.server.product.domain.exception.InvalidProductIdException;
import kr.hhplus.be.server.product.domain.exception.InvalidProductQuantityException;
import kr.hhplus.be.server.product.infra.ProductFakeRepository;
import kr.hhplus.be.server.shared.exception.InvalidUserException;
import kr.hhplus.be.server.user.wallet.domain.Wallet;
import kr.hhplus.be.server.user.wallet.domain.WalletRepository;
import kr.hhplus.be.server.user.wallet.infra.WalletFakeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("주문 기능 유닛 테스트")
@ExtendWith(MockitoExtension.class)
public class OrderUnitTest {
    private static final long USER_ID = 123L;
    private static final long WRONG_USER_ID = 1230L;
    private static final long PRODUCT_ID = 234L;
    private static final long WRONG_PRODUCT_ID = 234223L;

    @DisplayName("1~N 개 주문 기능 테스트")
    @Nested
    class OrderProductTest {
        @DisplayName("재고가 충분한 경우, 1 개 정상 주문이 가능 해야함.")
        @Test
        public void ShouldOrderOneProductWithSufficientStock() {
            var or = mock(OrderRepository.class);
            var wr = mock(WalletRepository.class);
            var pr = mock(ProductRepository.class);

            var expectedWallet = Wallet.builder()
                    .userId(USER_ID)
                    .balance(500)
                    .build();
            when(wr.findByUserId(anyLong())).thenReturn(Optional.of(expectedWallet));

            var expectedProduct = Product.builder()
                    .id(PRODUCT_ID)
                    .name("banana")
                    .price(500 - 100)
                    .quantity(100)
                    .build();
            when(pr.findByProductId(anyLong())).thenReturn(Optional.of(expectedProduct));

            var expectedOrder = Order.builder()
                    .id(1L)
                    .build();
            when(or.save(any())).thenReturn(expectedOrder);

            var svc = new OrderService(or, wr, pr);
            final int orderQuantity = 1;
            OrderProductResponse resp = svc.orderProduct(USER_ID, PRODUCT_ID, orderQuantity);

            assert resp.isOrdered();
            var o = resp.order();
            assert o.getId().equals(expectedOrder.getId());

            // test an ordered user.
            var user = o.getUser();
            assert user != null;
            assert user.getId() == USER_ID;

            // test a ordered product
            var product = o.getOrderToProducts().get(0).getProduct();
            assert product != null;
            assert product.getId() == PRODUCT_ID;
            assert product.getQuantity() == 100 - orderQuantity;
            assert product.getPrice() == 500 - 100;
            assert product.getName().equals("banana");
        }

        @DisplayName("재고가 충분한 경우, N 개 정상 주문이 가능 해야함.")
        @Test
        public void ShouldOrderManyProductsWithSufficientStock() {

            var or = mock(OrderRepository.class);
            var wr = mock(WalletRepository.class);
            var pr = mock(ProductRepository.class);

            var expectedWallet = Wallet.builder()
                    .userId(USER_ID)
                    .balance(50000)
                    .build();
            when(wr.findByUserId(anyLong())).thenReturn(Optional.of(expectedWallet));

            var expectedProduct = Product.builder()
                    .id(PRODUCT_ID)
                    .name("banana")
                    .price(500 - 100)
                    .quantity(100)
                    .build();
            when(pr.findByProductId(anyLong())).thenReturn(Optional.of(expectedProduct));

            var expectedOrder = Order.builder()
                    .id(1L)
                    .build();
            when(or.save(any())).thenReturn(expectedOrder);

            var svc = new OrderService(or, wr, pr);
            final int orderQuantity = 10;
            OrderProductResponse resp = svc.orderProduct(USER_ID, PRODUCT_ID, orderQuantity);

            assert resp.isOrdered();
            var o = resp.order();
            assert o.getId().equals(expectedOrder.getId());

            // test an ordered user.
            var user = o.getUser();
            assert user != null;
            assert user.getId() == USER_ID;

            // test an ordered product
            var product = o.getOrderToProducts().get(0).getProduct();
            assert product != null;
            assert product.getId() == PRODUCT_ID;
            assert product.getQuantity() == 100 - orderQuantity;
            assert product.getPrice() == 500 - 100;
            assert product.getName().equals("banana");
        }

        @DisplayName("주문 상품의 수량이 0 <= 이면 InvalidProductException 을 던지고, 주문을 취소한다.")
        @Test
        public void ShouldNeverOrderWithNegativeQuantity() {
            var or = mock(OrderRepository.class);
            var wr = mock(WalletRepository.class);
            var pr = mock(ProductRepository.class);

            var svc = new OrderService(or, wr, pr);
            final int orderQuantity = -23;
            var ex = assertThrows(
                    InvalidProductQuantityException.class,
                    () -> svc.orderProduct(USER_ID, PRODUCT_ID, orderQuantity));

            assert ex.getMessage().equals(
                    InvalidProductQuantityException.MakeMessage(PRODUCT_ID, orderQuantity));
        }

        @DisplayName("비정상적인 user ID 를 받으면 InvalidUserException 를 던지고, 주문을 취소한다.")
        @Test
        public void ShouldThrowInvalidUserIdException() {
            var or = mock(OrderRepository.class);
            var pr = mock(ProductRepository.class);
            var fakeWallet = Wallet.builder()
                    .userId(USER_ID)
                    .build();
            var wr = new WalletFakeRepository(fakeWallet);

            var svc = new OrderService(or, wr, pr);
            final int orderQuantity = 1;
            var ex = assertThrows(
                    InvalidUserException.class,
                    () -> svc.orderProduct(WRONG_USER_ID, PRODUCT_ID, orderQuantity));

            assert ex.getMessage().equals(
                    InvalidUserException.MakeMessage(WRONG_USER_ID));
        }

        @DisplayName("비정상적인 product ID 를 받으면 InvalidProductIdException 를 던지고, 주문을 취소한다.")
        @Test
        public void ShouldThrowInvalidProductIdException() {
            var or = mock(OrderRepository.class);
            var wr = mock(WalletRepository.class);
            when(wr.findByUserId(anyLong())).thenReturn(Optional.of(
                    Wallet.builder()
                            .userId(USER_ID)
                            .build()
            ));

            var fakeProduct = Product.builder()
                    .id(PRODUCT_ID)
                    .quantity(1)
                    .build();
            var pr = new ProductFakeRepository(fakeProduct);

            var svc = new OrderService(or, wr, pr);
            final int orderQuantity = 1;
            var ex = assertThrows(
                    InvalidProductIdException.class,
                    () -> svc.orderProduct(USER_ID, WRONG_PRODUCT_ID, orderQuantity));

            assert ex.getMessage().equals(
                    InvalidProductIdException.MakeMessage(WRONG_PRODUCT_ID));
        }

        @DisplayName("주문 하고자 하는 양 보다 재고가 부족하면 주문이 취소되고, InsufficientProductStockException 을 던짐.")
        @Test
        public void ShouldThrowInsufficientProductStockException() {

            var or = mock(OrderRepository.class);
            var wr = mock(WalletRepository.class);
            when(wr.findByUserId(anyLong())).thenReturn(Optional.of(
                    Wallet.builder()
                            .build()
            ));

            var fakeProduct = Product.builder()
                    .id(PRODUCT_ID)
                    .quantity(1)
                    .build();
            var pr = new ProductFakeRepository(fakeProduct);

            var svc = new OrderService(or, wr, pr);
            final int orderQuantity = 10;
            var ex = assertThrows(
                    InsufficientProductStockException.class,
                    () -> svc.orderProduct(USER_ID, PRODUCT_ID, orderQuantity));

            assert ex.getMessage().equals(
                    InsufficientProductStockException.MakeMessage(PRODUCT_ID, orderQuantity));
        }

        @DisplayName("주문 후 재고의 차감이 정상적이어야 함.")
        @Test
        public void ShouldProductStockRemainAsItWasOrdered() {

            var or = mock(OrderRepository.class);
            var wr = mock(WalletRepository.class);

            var expectedWallet = Wallet.builder()
                    .userId(USER_ID)
                    .balance(50000)
                    .build();
            when(wr.findByUserId(anyLong())).thenReturn(Optional.of(expectedWallet));

            var expectedOrder = Order.builder()
                    .id(1L)
                    .build();
            when(or.save(any())).thenReturn(expectedOrder);

            var fakeProduct = Product.builder()
                    .id(PRODUCT_ID)
                    .quantity(500)
                    .build();
            var pr = new ProductFakeRepository(fakeProduct);

            var svc = new OrderService(or, wr, pr);
            final int orderQuantity = 250;
            svc.orderProduct(USER_ID, PRODUCT_ID, orderQuantity);

            Optional<Product> found = pr.findByProductId(PRODUCT_ID);
            assert found.isPresent();
            Product foundUnwrap = found.get();
            assert foundUnwrap.getQuantity() == 500 - orderQuantity;
        }

        @DisplayName("주문 이 성공한 후, 잔액 차감이 정상적이어야 함.")
        @Test
        public void ShouldBalanceRemainAsOrderSucceeds() {

            var or = mock(OrderRepository.class);
            var pr = mock(ProductFakeRepository.class);
            var fakeWallet = Wallet.builder()
                    .userId(USER_ID)
                    .balance(500)
                    .build();
            var wr = new WalletFakeRepository(fakeWallet);

            var fakeProduct = Product.builder()
                    .id(PRODUCT_ID)
                    .quantity(500)
                    .price(100)
                    .build();
            when(pr.findByProductId(anyLong())).thenReturn(Optional.of(fakeProduct));

            var expectedOrder = Order.builder()
                    .id(1L)
                    .build();
            when(or.save(any())).thenReturn(expectedOrder);

            var svc = new OrderService(or, wr, pr);
            final int orderQuantity = 250;
            svc.orderProduct(USER_ID, PRODUCT_ID, orderQuantity);

            Optional<Wallet> found = wr.findByUserId(USER_ID);
            assert found.isPresent();
            Wallet foundUnwrap = found.get();
            assert foundUnwrap.getBalance() == fakeWallet.getBalance() - fakeProduct.getPrice() * orderQuantity;
        }
    }

    @DisplayName("주문 실패시의 롤백 테스팅")
    @Nested
    class OrderRollbackTest {

        @DisplayName("잔액이 없는 경우 주문을 실패처리하고, 롤백처리 함.")
        @Test
        public void ShouldRollbackIfBalanceIsInsufficient() {

            var or = mock(OrderRepository.class);
            var expectedOrder = Order.builder()
                    .id(1L)
                    .build();
            when(or.save(any())).thenReturn(expectedOrder);

            var expectedWallet = Wallet.builder()
                    .balance(10)
                    .build();
            var wr = mock(WalletRepository.class);
            when(wr.findByUserId(anyLong())).thenReturn(Optional.of(expectedWallet));

            var fakeProduct = Product.builder()
                    .id(PRODUCT_ID)
                    .quantity(10_000)
                    .price(10_000)
                    .build();
            var pr = mock(ProductRepository.class);
            when(pr.findByProductId(anyLong())).thenReturn(Optional.of(fakeProduct));

            var svc = new OrderService(or, wr, pr);
            final int orderQuantity = 250;
            svc.orderProduct(USER_ID, PRODUCT_ID, orderQuantity);

        }
    }
}
