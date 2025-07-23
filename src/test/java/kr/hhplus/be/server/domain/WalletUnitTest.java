package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.shared.exception.InvalidUserException;
import kr.hhplus.be.server.user.app.WalletService;
import kr.hhplus.be.server.user.domain.Wallet;
import kr.hhplus.be.server.user.domain.WalletRepository;
import kr.hhplus.be.server.user.domain.excpetion.NegativePointException;
import kr.hhplus.be.server.user.presentation.dto.ChargeBalanceResponse;
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
        public void ShouldChargePositivePoints() {
            final long WRONG_USER_ID = 1231254L;
            var repo = Mockito.mock(WalletRepository.class);
            when(repo.findByUserId(WRONG_USER_ID)).thenReturn(null);

            WalletService svc = new WalletService(repo);
            var ex = assertThrows(
                    InvalidUserException.class,
                    () -> svc.chargeBalance(WRONG_USER_ID, 1000));

            assert ex.getMessage().equals(InvalidUserException.MakeMessage(WRONG_USER_ID));
        }


        @DisplayName("양수 포인트는 정상 충전되어야 함.")
        @ParameterizedTest(name = "#{index} {0}")
        @ValueSource(ints = {1, 10, 100, 1000, 10_000, 100_000})
        public void ShouldChargePositivePoints(int point) {

            var actualWallet = Wallet.builder()
                    .balance(point)
                    .build();

            var repo = Mockito.mock(WalletRepository.class);
            when(repo.findByUserId(anyLong())).thenReturn(new Wallet());
            when(repo.save(any())).thenReturn(actualWallet);

            var svc = new WalletService(repo);

            ChargeBalanceResponse resp = svc.chargeBalance(USER_ID, point);
            assert resp.isCharged();
            assert resp.balanceAfterCharged() == point;
        }

        @DisplayName("음수 포인트는 충전되면 안 됨.")
        @ParameterizedTest(name = "#{index} {0}")
        @ValueSource(ints = {-1, -10, -100, -1000, -10_000, -100_000})
        public void ShouldNeverChargeNegativePoints(int point) {
            var repo = Mockito.mock(WalletRepository.class);
            var svc = new WalletService(repo);

            var ex = assertThrows(NegativePointException.class,
                    () -> svc.chargeBalance(USER_ID, point));
            assert ex.getMessage().equals(NegativePointException.MakeMsg(point));
        }


        @DisplayName("여러번 충전한 결과가 동일해야 함")
        @ParameterizedTest(name = "#{index} sum of {0} = {1}")
        @MethodSource("sourcesAndExpect")
        public void ShouldStackUpAfterMultipleCharge(List<Integer> nums, int expect) {
            var repo = Mockito.mock(WalletRepository.class);

            final int sum = nums.stream()
                    .reduce(0, Integer::sum);

            var actualWallet = Wallet.builder()
                    .balance(sum)
                    .userId(USER_ID)
                    .build();

            when(repo.findByUserId(anyLong())).thenReturn(new Wallet());
            when(repo.save(any())).thenReturn(actualWallet);

            var svc = new WalletService(repo);
            ChargeBalanceResponse resp = svc.chargeBalance(USER_ID, sum);

            assert resp.isCharged();
            assert resp.balanceAfterCharged() == expect;
        }

        static Stream<Arguments> sourcesAndExpect() {
            return Stream.of(
                    Arguments.of(List.of(100, 1000, 10_000, 100_000), 111_100),
                    Arguments.of(List.of(0, 100, 200, 300, 400), 1000)
            );
        }
    }
}
