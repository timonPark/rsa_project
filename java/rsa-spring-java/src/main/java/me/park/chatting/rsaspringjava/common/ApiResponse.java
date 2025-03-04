package me.park.chatting.rsaspringjava.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ApiResponse<T> {
  public HttpStatus status;
  public String message;
  public T data;
}
