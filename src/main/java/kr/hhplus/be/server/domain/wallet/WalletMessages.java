package kr.hhplus.be.server.domain.wallet;

// TODO: 임시. 도메인 설계 이후에 리팩토링
public final class WalletMessages {
    public static final String CHARGE_SUCCESS = "금액 충전을 완료했습니다.";
    public static final String CHARGE_INVALID_AMOUNT = "충전하고자하는 금액은 0 이하 일 수 없습니다.";
    public static final String BALANCE_QUERY_SUCCESS = "잔액을 조회하였습니다.";

    private WalletMessages() {
    }
}