package taskTracker.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import taskTracker.model.Task;

@RestController
@RequestMapping(path = "/tasks")
public class TaskController {

  @Autowired
  private TaskService taskService;

  //TODO: update task, set task finished


  @GetMapping("/{taskId}")
  @ResponseStatus(HttpStatus.OK)
  public Task getTask(@PathVariable("taskId") Long id) {
    return taskService.findTask(id);
  }

  @DeleteMapping("/{taskId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTask(@PathVariable("taskId") Long id) {
    taskService.deleteTask(id);
  }
}
