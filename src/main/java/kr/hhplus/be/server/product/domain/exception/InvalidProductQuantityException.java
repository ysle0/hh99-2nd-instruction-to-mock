package kr.hhplus.be.server.product.domain.exception;

public class InvalidProductQuantityException extends RuntimeException {
    public InvalidProductQuantityException(long productId, int quantity) {
        super(MakeMessage(productId, quantity));
    }

    public static String MakeMessage(long productId, int quantity) {
        return "invalid product quantity. productId: %d, quantity: %d"
                .formatted(productId, quantity);
    }
}
