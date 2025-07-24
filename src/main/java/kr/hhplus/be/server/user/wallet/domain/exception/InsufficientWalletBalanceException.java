package kr.hhplus.be.server.user.wallet.domain.exception;

public class InsufficientWalletBalanceException extends RuntimeException {
    public InsufficientWalletBalanceException(int amount) {
        super(MakeMessage(amount));
    }

    public static String MakeMessage(int amount) {
        return "insufficient balance. amount: %d".formatted(amount);
    }
}
