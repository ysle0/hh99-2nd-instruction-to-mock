package kr.hhplus.be.server.user.wallet.domain;

public class WalletPolicy {
    public static final int MAX_CHARGE_AMOUNT_AT_ONCE = 100_000_000;
    public static final int MIN_CHARGE_AMOUNT_AT_ONCE = 5_000;

    public static void validateChargeAmount(int amount) {
        if (amount < WalletPolicy.MIN_CHARGE_AMOUNT_AT_ONCE) {
            throw new PolicyBelowMinChargeAmountException(amount);
        }

        if (amount > WalletPolicy.MAX_CHARGE_AMOUNT_AT_ONCE) {
            throw new PolicyAboveMaxChargeAmountException(amount);
        }
    }
}
