package kr.hhplus.be.server.user.wallet.infra;

import kr.hhplus.be.server.user.wallet.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletJpaRepository extends JpaRepository<Wallet, Long> {
}
