package taskTracker.exception;

public class NoAccessToUserException extends RuntimeException {
  public NoAccessToUserException(Long id) {
    super("User: " + id + ", has no access to user's details");
  }
}
