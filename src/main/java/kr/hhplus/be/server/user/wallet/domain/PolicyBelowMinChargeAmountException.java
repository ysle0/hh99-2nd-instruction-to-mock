package kr.hhplus.be.server.user.wallet.domain;

public class PolicyBelowMinChargeAmountException extends RuntimeException {
    public PolicyBelowMinChargeAmountException(int point) {
        super(MakeMessage(point));
    }

    public static String MakeMessage(int point) {
        return "below minimum charge amount is not allowed. point: %d".formatted(point);
    }
}
