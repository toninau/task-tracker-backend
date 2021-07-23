package taskTracker.appuser;

public class AppUserNotFoundException extends RuntimeException {
  AppUserNotFoundException(Long id) {
    super("User " + id + ", does not exist");
  }
}
