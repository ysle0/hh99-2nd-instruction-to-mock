package kr.hhplus.be.server.user.wallet.app;

import kr.hhplus.be.server.shared.exception.InvalidUserException;
import kr.hhplus.be.server.user.wallet.domain.*;
import kr.hhplus.be.server.user.wallet.presentation.dto.ChargeBalanceResponse;
import kr.hhplus.be.server.user.wallet.presentation.dto.ShowBalanceResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository walletRepo;

    public WalletService(
            WalletRepository wr
    ) {
        this.walletRepo = wr;
    }

    public ChargeBalanceResponse chargeBalance(long userId, int newBalance) {
        if (newBalance < WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE) {
            throw new PolicyBelowMinChargeAmountException(newBalance);
        }

        if (newBalance > WalletPolicy.MAX_CHARGE_AMOUNT_AT_ONCE) {
            throw new PolicyAboveMaxChargeAmountException(newBalance);
        }

        Optional<Wallet> found = walletRepo.findByUserId(userId);
        if (found.isEmpty()) {
            throw new InvalidUserException(userId);
        }

        var foundUnwrap = found.get();
        final int prvBalance = foundUnwrap.getBalance();
        final int chargedBalance = prvBalance + newBalance;
        foundUnwrap.setBalance(chargedBalance);
        Wallet saved = walletRepo.save(foundUnwrap);

        return new ChargeBalanceResponse(true, saved.getBalance());
    }

    public ShowBalanceResponse showBalance(Long userId) {
        Optional<Wallet> found = walletRepo.findByUserId(userId);
        if (found.isEmpty()) {
            throw new InvalidUserException(userId);
        }

        Wallet foundUnwrap = found.get();
        return new ShowBalanceResponse(foundUnwrap.getBalance());
    }
}
