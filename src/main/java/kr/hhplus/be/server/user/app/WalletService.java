package kr.hhplus.be.server.user.app;

import kr.hhplus.be.server.shared.exception.InvalidUserException;
import kr.hhplus.be.server.user.domain.Wallet;
import kr.hhplus.be.server.user.domain.WalletRepository;
import kr.hhplus.be.server.user.domain.excpetion.NegativePointException;
import kr.hhplus.be.server.user.presentation.dto.ChargeBalanceResponse;
import kr.hhplus.be.server.user.presentation.dto.ShowBalanceResponse;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepository walletRepo;

    public WalletService(
            WalletRepository wr
    ) {
        this.walletRepo = wr;
    }

    public ChargeBalanceResponse chargeBalance(long userId, int newBalance) {
        if (newBalance < 0) {
            throw new NegativePointException(newBalance);
        }

        Wallet found = walletRepo.findByUserId(userId);
        if (found == null) {
            throw new InvalidUserException(userId);
        }

        final int prvBalance = found.getBalance();
        final int chargedBalance = prvBalance + newBalance;
        found.setBalance(chargedBalance);
        Wallet saved = walletRepo.save(found);

        return new ChargeBalanceResponse(true, saved.getBalance());
    }

    public ShowBalanceResponse showBalance(Long userId) {
        Wallet found = walletRepo.findByUserId(userId);
        if (found == null) {
            return new ShowBalanceResponse(0);
        }
        return new ShowBalanceResponse(found.getBalance());
    }
}
