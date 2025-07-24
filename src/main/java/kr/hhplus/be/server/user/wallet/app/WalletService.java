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

    public ChargeBalanceResponse chargeBalance(long userId, int chargeAmount) {
        // 1. 도메인 객체 조회
        Wallet wallet = findWalletByUserId(userId);
        
        // 2. 도메인 객체에게 비즈니스 로직 위임 (정책 검증 포함)
        wallet.charge(chargeAmount);
        
        // 3. 저장 (트랜잭션 경계 관리)
        Wallet saved = walletRepo.save(wallet);

        return new ChargeBalanceResponse(true, saved.getBalance());
    }

    public ShowBalanceResponse showBalance(Long userId) {
        // 1. 도메인 객체 조회
        Wallet wallet = findWalletByUserId(userId);
        
        return new ShowBalanceResponse(wallet.getBalance());
    }
    
    // === 도메인 객체 조회 헬퍼 메서드 ===
    
    private Wallet findWalletByUserId(long userId) {
        Optional<Wallet> foundWallet = walletRepo.findByUserId(userId);
        if (foundWallet.isEmpty()) {
            throw new InvalidUserException(userId);
        }
        return foundWallet.get();
    }
}
