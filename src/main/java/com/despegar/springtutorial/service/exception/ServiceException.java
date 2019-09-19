package com.despegar.springtutorial.service.exception;

public class ServiceException extends RuntimeException {
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
