package taskTracker.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import taskTracker.task.TaskNotFoundException;
import taskTracker.taskgroup.TaskGroupNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({TaskGroupNotFoundException.class, TaskNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  ExceptionResponse taskGroupNotFoundHandler(HttpServletRequest req, RuntimeException e) {
    return new ExceptionResponse(req.getRequestURL().toString(), e.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ExceptionResponse validationExceptionHandler(HttpServletRequest req, ConstraintViolationException e) {
    Map<String, String> errorFields = new HashMap<>();

    e.getConstraintViolations().forEach((error) -> {
      String fieldName = error.getPropertyPath().toString();
      String errorMessage = error.getMessage();
      errorFields.put(fieldName, errorMessage);
    });

    return new ExceptionResponse(req.getRequestURL().toString(),
        "Validation errors", errorFields);
  }
}
