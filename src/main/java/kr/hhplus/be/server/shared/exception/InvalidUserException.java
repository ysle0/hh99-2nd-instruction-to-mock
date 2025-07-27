package kr.hhplus.be.server.shared.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(long userId) {
        super(MakeMessage(userId));
    }

    public static String MakeMessage(long userId) {
        return "invalid user id was provided. userId: %d".formatted(userId);
    }
}
