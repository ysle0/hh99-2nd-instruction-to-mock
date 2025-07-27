package kr.hhplus.be.server.product.domain.exception;

public class InvalidProductIdException extends RuntimeException {
    public InvalidProductIdException(long productId) {
        super(MakeMessage(productId));
    }

    public static String MakeMessage(long productId) {
        return "invalid product id was provided. productId: %d".formatted(productId);
    }
}
