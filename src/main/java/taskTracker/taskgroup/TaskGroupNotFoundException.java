package taskTracker.taskgroup;

public class TaskGroupNotFoundException extends RuntimeException {
  public TaskGroupNotFoundException(Long id) {
    super("Task group " + id + " does not exist");
  }
}
