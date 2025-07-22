package kr.hhplus.be.server.user.infra;

import kr.hhplus.be.server.user.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletJpaRepository extends JpaRepository<Wallet, Long> {
}
