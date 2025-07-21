package kr.hhplus.be.server.infra.mysql.jpa;

import kr.hhplus.be.server.domain.wallet.Wallet;
import kr.hhplus.be.server.domain.wallet.WalletRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletJpaRepository
        extends WalletRepository,
        JpaRepository<Wallet, Long> {
}
