package kr.hhplus.be.server.user.app;

import kr.hhplus.be.server.user.infra.WalletJpaRepository;
import kr.hhplus.be.server.user.presentation.dto.ChargeBalanceResponse;
import kr.hhplus.be.server.user.presentation.dto.ShowBalanceResponse;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletJpaRepository walletRepo;

    public WalletService(
            WalletJpaRepository wr
    ) {
        this.walletRepo = wr;
    }

    public ChargeBalanceResponse chargeBalance(long userId, int newBalance) {
        return null;
    }

    public ShowBalanceResponse showBalance(Long userId) {
        return null;
    }
}
