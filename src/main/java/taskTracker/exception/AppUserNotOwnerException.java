package taskTracker.exception;

public class AppUserNotOwnerException extends RuntimeException {
  public AppUserNotOwnerException(Long id) {
    super("User " + id + ", is not the owner of the group");
  }
}
