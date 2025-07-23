package kr.hhplus.be.server.user.domain.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegativePointException extends RuntimeException {
    public NegativePointException(int point) {
        super(MakeMsg(point));
    }

    public static String MakeMsg(int point) {
        return "Negative point is not allowed. point was %d".formatted(point);
    }
}
