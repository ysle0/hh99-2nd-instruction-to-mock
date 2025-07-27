package kr.hhplus.be.server.product.domain.exception;

public class InsufficientProductStockException extends RuntimeException {
    public InsufficientProductStockException(long productId, int orderedQuantity) {
        super(MakeMessage(productId, orderedQuantity));
    }

    public static String MakeMessage(long productId, int orderedQuantity) {
        return "productId %d has insufficient stock. ordered quantity: %d".formatted(productId, orderedQuantity);
    }
}
