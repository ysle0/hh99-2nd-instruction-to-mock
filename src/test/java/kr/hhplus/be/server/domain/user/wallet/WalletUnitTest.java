package kr.hhplus.be.server.domain.user.wallet;

import kr.hhplus.be.server.shared.exception.InvalidUserException;
import kr.hhplus.be.server.user.wallet.app.WalletService;
import kr.hhplus.be.server.user.wallet.domain.*;
import kr.hhplus.be.server.user.wallet.infra.WalletFakeRepository;
import kr.hhplus.be.server.user.wallet.presentation.dto.ChargeBalanceResponse;
import kr.hhplus.be.server.user.wallet.presentation.dto.ShowBalanceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@DisplayName("지갑 기능 유닛 테스트")
@ExtendWith(MockitoExtension.class)
public class WalletUnitTest {
    private static final long USER_ID = 123L;

    @DisplayName("충전기능 테스팅")
    @Nested
    class ChargeTest {
        @DisplayName("가입되어 있지 않은 유저는 InvalidUserIdException 을 던짐.")
        @Test
        public void ShouldThrowInvalidUserException() {
            final long WRONG_USER_ID = 12354L;
            var repo = Mockito.mock(WalletRepository.class);

            WalletService svc = new WalletService(repo);
            var ex = assertThrows(
                    InvalidUserException.class,
                    () -> svc.chargeBalance(WRONG_USER_ID, WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE + 1));

            assert ex.getMessage().equals(InvalidUserException.MakeMessage(WRONG_USER_ID));
        }

        @DisplayName("최소 충전금액보다 작은 금액은 PolicyBelowMinChargeAmountException 을 던짐.")
        @Test
        public void ShouldNeverChargeBelowMinimumBalances() {

            final int belowBalance = WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE - 232;
            var repo = Mockito.mock(WalletRepository.class);
            var svc = new WalletService(repo);

            var ex = assertThrows(PolicyBelowMinChargeAmountException.class,
                    () -> svc.chargeBalance(USER_ID, belowBalance));
            assert ex.getMessage().equals(PolicyBelowMinChargeAmountException.MakeMessage(belowBalance));
        }

        @DisplayName("최대 충전금액보다 큰 금액은 PolicyAboveMaxChargeAmountException 을 던짐.")
        @Test
        public void ShouldNeverChargeAboveMaximumBalances() {

            final int maxBalance = WalletPolicy.MAX_CHARGE_AMOUNT_AT_ONCE + 232;
            var repo = Mockito.mock(WalletRepository.class);
            var svc = new WalletService(repo);

            var ex = assertThrows(PolicyAboveMaxChargeAmountException.class,
                    () -> svc.chargeBalance(USER_ID, maxBalance));
            assert ex.getMessage().equals(PolicyAboveMaxChargeAmountException.MakeMessage(maxBalance));
        }

        @DisplayName("양수 포인트는 정상 충전되어야 함. (최소, 최대) 포인트 충전 포함")
        @ParameterizedTest(name = "#{index} {0}")
        @ValueSource(ints = {WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE, 10_000, 100_000, WalletPolicy.MAX_CHARGE_AMOUNT_AT_ONCE})
        public void ShouldChargePositiveBalance(int balance) {

            var actualWallet = Wallet.builder()
                    .balance(balance)
                    .build();

            var repo = Mockito.mock(WalletRepository.class);
            when(repo.findByUserId(anyLong())).thenReturn(Optional.of(new Wallet()));
            when(repo.save(any())).thenReturn(actualWallet);

            var svc = new WalletService(repo);

            ChargeBalanceResponse resp = svc.chargeBalance(USER_ID, balance);
            assert resp.isCharged();
            assert resp.balanceAfterCharged() == balance;
        }

        @DisplayName("여러번 충전한 결과가 동일해야 함")
        @ParameterizedTest(name = "#{index} sum of {0} = {1}")
        @MethodSource("sourcesAndExpect")
        public void ShouldStackUpAfterMultipleCharge(List<Integer> srcs, List<Integer> expects) {
            var fakeWallet = Wallet.builder()
                    .userId(USER_ID)
                    .build();
            var repo = new WalletFakeRepository(fakeWallet);
            var svc = new WalletService(repo);

            List<ChargeBalanceResponse> resps = srcs.stream()
                    .map(n -> svc.chargeBalance(USER_ID, n))
                    .toList();

            for (int i = 0; i < resps.size(); i++) {
                System.out.println(resps);
                var resp = resps.get(i);
                assert resp.isCharged();
                assert resp.balanceAfterCharged() == expects.get(i);
            }
        }

        static Stream<Arguments> sourcesAndExpect() {
            return Stream.of(
                    Arguments.of(
                            List.of(WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE, 10_000, 100_000),
                            List.of(WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE, 15_000, 115_000)),
                    Arguments.of(
                            List.of(WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE, 100_000, 10_000_000),
                            List.of(WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE, 105_000, 10_105_000))
            );
        }
    }

    @DisplayName("잔액 조회기능 테스팅")
    @Nested
    class ShowBalanceTest {
        @DisplayName("가입되어 있지 않은 유저는 InvalidUserIdException 을 던짐.")
        @Test
        public void ShouldThrowInvalidUserException() {
            final long WRONG_USER_ID = 1231254L;
            var repo = Mockito.mock(WalletRepository.class);

            WalletService svc = new WalletService(repo);
            var ex = assertThrows(
                    InvalidUserException.class,
                    () -> svc.showBalance(WRONG_USER_ID));

            assert ex.getMessage().equals(InvalidUserException.MakeMessage(WRONG_USER_ID));
        }


        @DisplayName("충전한 포인트를 그대로 조회 가능 해야함.")
        @ParameterizedTest(name = "#{index} {0}")
        @ValueSource(ints = {WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE, 10_000, 100_000})
        public void ShouldShowChargedBalance(int balance) {

            var actualWallet = Wallet.builder()
                    .balance(balance)
                    .build();

            var repo = Mockito.mock(WalletRepository.class);
            when(repo.findByUserId(anyLong())).thenReturn(Optional.of(new Wallet()));
            when(repo.save(any())).thenReturn(actualWallet);

            var svc = new WalletService(repo);

            ChargeBalanceResponse resp = svc.chargeBalance(USER_ID, balance);
            assert resp.isCharged();
            assert resp.balanceAfterCharged() == balance;

            ShowBalanceResponse showResp = svc.showBalance(USER_ID);
            assert showResp.balance() == balance;
        }

        @DisplayName("여러번 충전한 포인트를 그대로 조회 가능 해야함.")
        @ParameterizedTest(name = "#{index} sum of {0} = {1}")
        @MethodSource("sourcesAndExpect")
        public void ShouldShowMultipleChargedBalances(List<Integer> srcs, List<Integer> expects) {

            var fakeWallet = Wallet.builder()
                    .userId(USER_ID)
                    .build();
            var repo = new WalletFakeRepository(fakeWallet);
            var svc = new WalletService(repo);

            List<ChargeBalanceResponse> resps = srcs.stream()
                    .map(x -> svc.chargeBalance(USER_ID, x))
                    .toList();

            for (int i = 0; i < resps.size(); i++) {
                var r = resps.get(i);
                assert r.isCharged();
                assert r.balanceAfterCharged() == expects.get(i);
            }

            ShowBalanceResponse showResp = svc.showBalance(USER_ID);
            assert showResp.balance() == expects.get(expects.size() - 1);
        }

        static Stream<Arguments> sourcesAndExpect() {
            return Stream.of(
                    Arguments.of(
                            List.of(WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE, 10_000, 100_000),
                            List.of(WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE, 15_000, 115_000)),
                    Arguments.of(
                            List.of(WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE, 100_000, 10_000_000),
                            List.of(WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE, 105_000, 10_105_000))
            );
        }
    }
}
