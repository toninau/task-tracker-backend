package taskTracker.taskGroup;

public class TaskGroupNotFoundException extends RuntimeException {
  TaskGroupNotFoundException(Long id) {
    super("Task group " + id + " does not exist");
  }
}
