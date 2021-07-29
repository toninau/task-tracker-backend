package taskTracker.exception;

public class NoAccessToGroupException extends RuntimeException {
  public NoAccessToGroupException(Long id) {
    super("User: " + id + ", is not a member or the owner of the group");
  }
}
