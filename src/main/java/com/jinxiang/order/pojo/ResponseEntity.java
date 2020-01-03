package com.jinxiang.order.pojo;

/**
 * 返回值实体类
 */
public class ResponseEntity<T> {

    private int code;

    private Boolean isSuccess;

    private String message;

    private T result;

    public ResponseEntity(){}

    public ResponseEntity(int code, Boolean isSuccess, String message, T result) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
