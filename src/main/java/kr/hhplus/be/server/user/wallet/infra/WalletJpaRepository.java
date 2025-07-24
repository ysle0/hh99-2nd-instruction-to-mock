package kr.hhplus.be.server.user.wallet.infra;

import kr.hhplus.be.server.user.wallet.domain.Wallet;
import kr.hhplus.be.server.user.wallet.domain.WalletRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("walletJpaRepository")
public interface WalletJpaRepository extends WalletRepository, JpaRepository<Wallet, Long> {
}
