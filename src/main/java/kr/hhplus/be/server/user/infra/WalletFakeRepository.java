package kr.hhplus.be.server.user.infra;

import kr.hhplus.be.server.user.domain.Wallet;
import kr.hhplus.be.server.user.domain.WalletRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WalletFakeRepository implements WalletRepository {
    private final Map<Long, Wallet> store;

    public WalletFakeRepository(long fakeUserId) {
        this.store = new HashMap<>(1);
        var fakeWallet = Wallet.builder()
                .userId(fakeUserId)
                .build();
        store.put(fakeUserId, fakeWallet);
    }

    @Override
    public Optional<Wallet> findByUserId(long userId) {
        if (!store.containsKey(userId)) {
            return Optional.empty();
        }
        Wallet stored = store.get(userId);
        return Optional.of(stored);
    }

    @Override
    public Wallet save(Wallet walletWithNewBalance) {
        return store.put(walletWithNewBalance.getUserId(), walletWithNewBalance);
    }
}
