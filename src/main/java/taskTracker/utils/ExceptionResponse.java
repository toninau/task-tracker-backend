package taskTracker.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ExceptionResponse {
  private final String url;
  private final String message;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private final LocalDateTime timestamp;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final Map<String, String> errorFields;

  public ExceptionResponse(String url, String message) {
    this.url = url;
    this.message = message;
    this.timestamp = LocalDateTime.now();
    this.errorFields = null;
  }

  public ExceptionResponse(String url, String message, Map<String, String> errorFields) {
    this.url = url;
    this.message = message;
    this.timestamp = LocalDateTime.now();
    this.errorFields = errorFields;
  }
}
