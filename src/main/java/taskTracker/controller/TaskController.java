package taskTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import taskTracker.exception.NoAccessToGroupException;
import taskTracker.model.AppUser;
import taskTracker.model.AppUserDetails;
import taskTracker.model.Task;
import taskTracker.service.TaskGroupService;
import taskTracker.service.TaskService;

import java.util.Map;

@RestController
@RequestMapping(path = "/tasks")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @Autowired
  private TaskGroupService taskGroupService;

  @GetMapping("/{taskId}")
  @ResponseStatus(HttpStatus.OK)
  public Task getTask(@PathVariable("taskId") Long id, Authentication authentication) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();

    Task task = taskService.findTaskWithOwnerAndMembers(id);

    if (appUser.getId() != task.getTaskGroup().getOwner().getId() &&
        task.getTaskGroup().getMembers().stream().noneMatch(a -> a.getId() == appUser.getId())) {
      throw new NoAccessToGroupException(appUser.getId());
    }
    return task;
  }

  @PutMapping("/{taskId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateTask(
      @RequestBody Map<String, Boolean> requestBody,
      @PathVariable("taskId") Long id,
      Authentication authentication
  ) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();

    Task task = taskService.findTaskWithOwnerAndMembers(id);

    if (appUser.getId() != task.getTaskGroup().getOwner().getId() &&
        task.getTaskGroup().getMembers().stream().noneMatch(a -> a.getId() == appUser.getId())) {
      throw new NoAccessToGroupException(appUser.getId());
    }

    Boolean finished = requestBody.get("finished");

    if (finished != null) {
      task.setFinished(finished);
      taskService.updateTask(task);
    }
  }

  @DeleteMapping("/{taskId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Transactional
  public void deleteTask(@PathVariable("taskId") Long id, Authentication authentication) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();

    Task task = taskService.findTaskWithOwnerAndMembers(id);

    if (appUser.getId() != task.getTaskGroup().getOwner().getId() &&
        task.getTaskGroup().getMembers().stream().noneMatch(a -> a.getId() == appUser.getId())) {
      throw new NoAccessToGroupException(appUser.getId());
    }

    taskService.deleteTask(id);
  }
}
