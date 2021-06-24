package taskTracker.task;

public class TaskNotFoundException extends RuntimeException {
  TaskNotFoundException(Long id) {
    super("Task " + id + " does not exist");
  }
}
