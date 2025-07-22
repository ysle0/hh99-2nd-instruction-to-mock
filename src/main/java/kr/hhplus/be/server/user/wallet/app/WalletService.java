package kr.hhplus.be.server.user.wallet.app;

import kr.hhplus.be.server.user.wallet.infra.WalletJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletJpaRepository walletRepo;

    public WalletService(
            WalletJpaRepository wr
    ) {
        this.walletRepo = wr;
    }

    public kr.hhplus.be.server.user.wallet.presentation.dto.ChargeBalanceResponse chargeBalance(long userId, int newBalance) {
        return null;
    }

    public kr.hhplus.be.server.user.wallet.presentation.dto.ShowBalanceResponse showBalance(Long userId) {
        return null;
    }
}
