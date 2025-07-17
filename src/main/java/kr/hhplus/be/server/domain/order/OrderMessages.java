package kr.hhplus.be.server.domain.order;

// TODO: 임시. 도메인 설계 이후에 리팩토링
public final class OrderMessages {
    public static final String ORDER_SUCCESS = "상품이 주문되었습니다";
    public static final String ORDER_INVALID_QUANTITY = "0개 이하로 주문 불가능합니다.";

    private OrderMessages() {
    }
}