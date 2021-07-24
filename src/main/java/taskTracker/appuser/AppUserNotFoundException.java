package taskTracker.appuser;

public class AppUserNotFoundException extends RuntimeException {
  public AppUserNotFoundException(Long id) {
    super("User " + id + ", does not exist");
  }
}
