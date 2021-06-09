package com.example.opencvdemo.result;

/**
 * 封装API的错误码
 * @author aaron
 * @since 2020-09-22
 */
public interface IErrorCode {
    /**
     * 获取错误码
     * @return long
     */
    String getCode();

    /**
     * 获取错误信息
     * @return String
     */
    String getMessage();
}
