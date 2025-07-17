package kr.hhplus.be.server.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private String code;
    private T data;

    public ApiResponse(String message, String code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }
}
