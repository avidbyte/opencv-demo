package com.example.opencvdemo.result;



import java.io.Serializable;

/**
 * 通用返回对象
 *
 * @author aaron
 * @since 2020-09-22
 */
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = -6611408297660782952L;
    private String code;
    private String message;
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 返回的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 返回的数据
     * @param  message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ErrorCode.SUCCESS.getCode(), message, data);
    }

//    /**
//     * 成功返回结果
//     *
//     * @param message 提示信息
//     */
//    public static <T> CommonResult<T> success(String message){
//        return new CommonResult<T>(ErrorCode.SUCCESS.getCode(),message,null);
//    }



    /**
     * 通用失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }


    /**
     * 通用失败返回结果
     * @param errorCode 错误码
     * @param data 返回的数据
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode, T data) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), data);
    }

    /**
     * 通用失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
        return new CommonResult<T>(errorCode.getCode(), message, null);
    }

    public static <T> CommonResult<T> failed(String errorCode, String message) {
        return new CommonResult<T>(errorCode, message, null);
    }


    /**
     * 用户端错误
     * @param <T> T
     */
    public static <T> CommonResult<T> clientFailed() {
        return new CommonResult<T>(ErrorCode.A0001.getCode(), ErrorCode.A0001.getMessage(), null);
    }


    /**
     * 系统执行出错
     * @param <T> T
     */
    public static <T> CommonResult<T> systemFailed() {
        return new CommonResult<T>(ErrorCode.B0001.getCode(), ErrorCode.B0001.getMessage(), null);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return new CommonResult<T>(ErrorCode.A0400.getCode(), ErrorCode.A0400.getMessage(), null);
    }

    /**
     * 参数验证失败返回结果
     * @param message 错误信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ErrorCode.A0400.getCode(), message, null);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
