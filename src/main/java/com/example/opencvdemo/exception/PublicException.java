package com.example.opencvdemo.exception;


import com.example.opencvdemo.result.IErrorCode;

/**
 * @author aaron
 * @since 2020-10-10
 */
public class PublicException extends CommonException {

    private static final long serialVersionUID = 4790064787927579753L;

    public PublicException() {
        super();
    }

    public PublicException(IErrorCode iErrorCode) {
        super(iErrorCode.getCode(), iErrorCode.getMessage());
    }

    public PublicException(String code, String msg) {
        super(code, msg);
    }

    public PublicException(String msg) {
        super(msg);
    }
}
