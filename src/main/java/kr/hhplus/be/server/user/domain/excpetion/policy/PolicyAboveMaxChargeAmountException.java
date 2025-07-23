package kr.hhplus.be.server.user.domain.excpetion.policy;

public class PolicyAboveMaxChargeAmountException extends RuntimeException {
    public PolicyAboveMaxChargeAmountException(int point) {
        super(MakeMessage(point));
    }

    public static String MakeMessage(int point) {
        return "above maximum charge amount is not allowed. point: %d".formatted(point);
    }
}
