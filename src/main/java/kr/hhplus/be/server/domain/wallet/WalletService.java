package kr.hhplus.be.server.domain.wallet;

import kr.hhplus.be.server.infra.wallet.DummyWalletRepository;
import kr.hhplus.be.server.infra.wallet.WalletRepository;
import kr.hhplus.be.server.web.dto.ChargeBalanceReply;
import kr.hhplus.be.server.web.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.web.dto.ShowBalanceReply;
import kr.hhplus.be.server.web.dto.ShowBalanceRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {
    final WalletRepository walletRepository;

    public WalletService(
            DummyWalletRepository wr
    ) {
        walletRepository = wr;
    }

    public ShowBalanceReply showBalance(ShowBalanceRequest r) {
        Optional<Wallet> foundWallet = this.walletRepository.findById(r.id());
        if (foundWallet.isEmpty()) {
            return null;
        }

        var wallet = foundWallet.get();
        return new ShowBalanceReply(wallet.getBalance());
    }

    public ChargeBalanceReply chargeBalance(ChargeBalanceRequest r) {
        Optional<Wallet> foundWallet = this.walletRepository.findById(r.id());
        if (foundWallet.isEmpty()) {
            return null;
        }

        var wallet = Wallet.CopyFrom(foundWallet.get());
        wallet.setBalance(r.newBalance());

        Wallet savedWallet = this.walletRepository.save(wallet);

        return new ChargeBalanceReply();
    }
}
