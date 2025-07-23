package kr.hhplus.be.server.user.domain;

public interface WalletRepository {
    Wallet findByUserId(long userId);
    Wallet save(Wallet walletWithNewBalance);
}
