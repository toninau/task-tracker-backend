package taskTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import taskTracker.exception.AppUserNotOwnerException;
import taskTracker.exception.NoAccessToGroupException;
import taskTracker.model.*;
import taskTracker.service.AppUserService;
import taskTracker.service.TaskGroupService;
import taskTracker.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(path = "/groups")
public class TaskGroupController {

  @Autowired
  private TaskGroupService taskGroupService;

  @Autowired
  private AppUserService appUserService;

  @Autowired
  private TaskService taskService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public TaskGroup createGroup(@RequestBody TaskGroup taskGroup, Authentication authentication) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    taskGroup.setOwner(appUser);
    return taskGroupService.createGroup(taskGroup);
  }

  @PostMapping("/{groupId}/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public Task createTask(
      @PathVariable Long groupId,
      @RequestBody Task task,
      Authentication authentication
  ) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    TaskGroup taskGroup = taskGroupService.getTaskGroup(groupId);

    if (taskGroup.getOwner().getId() != appUser.getId() &&
        taskGroup.getMembers().stream().noneMatch(a -> a.getId() == appUser.getId())) {
      throw new NoAccessToGroupException(appUser.getId());
    }

    task.setTaskGroup(taskGroup);
    return taskService.createTask(task);
  }

  @PutMapping("/{groupId}/members/{appUserId}")
  @ResponseStatus(HttpStatus.OK)
  @Transactional
  public TaskGroup addMembers(
      @PathVariable Long groupId,
      @PathVariable Long appUserId,
      Authentication authentication
  ) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    TaskGroup groupToAdd = taskGroupService.getTaskGroup(groupId);

    if (groupToAdd.getOwner().getId() != appUser.getId()) {
      throw new AppUserNotOwnerException(appUser.getId());
    } else if (groupToAdd.getOwner().getId() == appUserId) {
      throw new IllegalStateException(String.format("user: %s, is the owner of the group", appUserId));
    } else if (groupToAdd.getMembers().stream().anyMatch(a -> a.getId() == appUserId)) {
      throw new IllegalStateException(String.format("user: %s, is already in group", appUserId));
    }

    AppUser appUser2 = appUserService.findUser(appUserId);
    groupToAdd.addMember(appUser2);

    return taskGroupService.updateGroup(groupToAdd);
  }

  @GetMapping("/{groupId}/tasks")
  @ResponseStatus(HttpStatus.OK)
  @Transactional
  public List<Task> getTasks(
      @PathVariable Long groupId,
      @RequestParam(defaultValue = "0") Integer page,
      Authentication authentication
  ) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    TaskGroup taskGroup = taskGroupService.getTaskGroup(groupId);

    if (taskGroup.getOwner().getId() != appUser.getId() &&
        taskGroup.getMembers().stream().noneMatch(a -> a.getId() == appUser.getId())) {
      throw new NoAccessToGroupException(appUser.getId());
    }

    return taskGroupService.groupTasks(taskGroup, page);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Transactional
  public TaskGroup getTaskGroup(@PathVariable Long id, Authentication authentication) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    TaskGroup taskGroup = taskGroupService.getTaskGroup(id);

    if (taskGroup.getOwner().getId() != appUser.getId() &&
        taskGroup.getMembers().stream().noneMatch(a -> a.getId() == appUser.getId())) {
      throw new NoAccessToGroupException(appUser.getId());
    }

    return taskGroup;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TaskGroup> getUserTaskGroups(Authentication authentication) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    List<TaskGroup> taskGroups = taskGroupService.getUserTaskGroups(appUser);
    return taskGroups;
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
