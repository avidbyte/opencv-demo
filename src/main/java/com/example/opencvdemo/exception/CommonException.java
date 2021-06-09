package com.example.opencvdemo.exception;

import com.example.opencvdemo.result.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author aaron
 * @since 2020-10-10
 */
@Getter
@Setter
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 489520656070244555L;

    protected String code;
    protected String message;

    public CommonException() {
        this.code= ErrorCode.B0001.getCode();
        this.message = ErrorCode.B0001.getMessage();
    }

    public CommonException(String msg) {
        this.code= ErrorCode.B0001.getCode();
        this.message = msg;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public CommonException(String code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
