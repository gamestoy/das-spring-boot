package com.despegar.dasboot.connector.exception;

public class APIException extends RuntimeException {
  private Integer code;

  public APIException(Integer code, String message) {
    super(message);
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }
}
