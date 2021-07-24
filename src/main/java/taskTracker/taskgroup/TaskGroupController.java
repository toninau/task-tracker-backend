package taskTracker.taskgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import taskTracker.model.Task;
import taskTracker.model.TaskGroup;
import taskTracker.service.TaskGroupService;

import java.util.List;

@RestController
@RequestMapping(path = "/groups")
public class TaskGroupController {

  @Autowired
  private TaskGroupService taskGroupService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskGroup createGroup(@RequestBody TaskGroup taskGroup) {
    return taskGroupService.createGroup(taskGroup);
  }

  @PostMapping("/{id}/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  public Task createTask(@PathVariable Long id, @RequestBody Task task) {
    return taskGroupService.createTask(id, task);
  }

  @GetMapping("/{id}/tasks")
  @ResponseStatus(HttpStatus.OK)
  public List<Task> getTasks(
      @PathVariable Long id,
      @RequestParam(defaultValue = "0") Integer page) {
    return taskGroupService.groupTasks(id, page);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public TaskGroup getTaskGroup(@PathVariable Long id) {
    return taskGroupService.getTaskGroup(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTaskGroup(@PathVariable Long id) {
    taskGroupService.deleteTaskGroup(id);
  }
}
