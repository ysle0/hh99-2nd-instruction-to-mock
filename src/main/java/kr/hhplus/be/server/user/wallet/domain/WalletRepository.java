package kr.hhplus.be.server.user.wallet.domain;

import java.util.Optional;

public interface WalletRepository {
    Optional<Wallet> findByUserId(long userId);

    Wallet save(Wallet walletWithNewBalance);
}
