package taskTracker.task;

public class TaskNotFoundException extends RuntimeException {
  public TaskNotFoundException(Long id) {
    super("Task " + id + " does not exist");
  }
}
