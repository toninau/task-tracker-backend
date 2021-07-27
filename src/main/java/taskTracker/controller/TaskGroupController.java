package taskTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import taskTracker.exception.AppUserNotOwnerException;
import taskTracker.model.AppUser;
import taskTracker.model.AppUserDetails;
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
  public TaskGroup createGroup(@RequestBody TaskGroup taskGroup, Authentication authentication) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    taskGroup.setOwner(appUser);
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
  @Transactional
  public void deleteTaskGroup(@PathVariable Long id, Authentication authentication) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    TaskGroup groupToDelete = taskGroupService.getTaskGroup(id);
    if (groupToDelete.getOwner().getId() != appUser.getId()) {
      throw new AppUserNotOwnerException(appUser.getId());
    }
    taskGroupService.deleteTaskGroup(id);
  }
}
