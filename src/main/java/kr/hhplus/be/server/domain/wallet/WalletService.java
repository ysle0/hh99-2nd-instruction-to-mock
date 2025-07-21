package kr.hhplus.be.server.domain.wallet;

import kr.hhplus.be.server.web.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.web.dto.ChargeBalanceResponse;
import kr.hhplus.be.server.web.dto.ShowBalanceRequest;
import kr.hhplus.be.server.web.dto.ShowBalanceResponse;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepository walletRepo;

    public WalletService(
            WalletRepository wr
    ) {
        this.walletRepo = wr;
    }

    public ChargeBalanceResponse chargeBalance(ChargeBalanceRequest r) {
        return null;
    }

    public ShowBalanceResponse showBalance(ShowBalanceRequest r) {
        return null;
    }
}
