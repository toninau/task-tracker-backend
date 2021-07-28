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
import taskTracker.repository.TaskGroupRepository;
import taskTracker.service.AppUserService;
import taskTracker.service.TaskGroupService;

import java.util.List;

@RestController
@RequestMapping(path = "/groups")
public class TaskGroupController {

  @Autowired
  private TaskGroupService taskGroupService;

  @Autowired
  private AppUserService appUserService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskGroup createGroup(@RequestBody TaskGroup taskGroup, Authentication authentication) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    //taskGroup.setOwner(appUser);
    return taskGroupService.createGroup(taskGroup);
  }

  @PostMapping("/{id}/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  public Task createTask(@PathVariable Long id, @RequestBody Task task) {
    return taskGroupService.createTask(id, task);
  }

  @PutMapping("/{groupId}/members/{appUserId}")
  @ResponseStatus(HttpStatus.OK)
  public TaskGroup addMembers(
      @PathVariable Long groupId,
      @PathVariable Long appUserId,
      Authentication authentication
  ) {
    /*AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();*/
    System.out.println("bearer token ^");

    System.out.println("========1======== (only 1 query)\n");
    TaskGroup group = taskGroupService.getTaskGroup(groupId);
    System.out.println(group);


    /*if (group.getOwner().getId() != appUser.getId()) {
      throw new AppUserNotOwnerException(appUser.getId());
    }*/

    System.out.println("========2======== (only 1 query)\n");
    AppUser appUser2 = appUserService.findUser(appUserId);
    System.out.println(appUser2);


    System.out.println("========3========\n");
    //group.getMembers().add(appUser2);
    group.addMember(appUser2);

    System.out.println("========4========\n");
    System.out.println(group);
    System.out.println(appUser2);

    System.out.println("========5========\n");
    return taskGroupService.createGroup(group);
  }

  /*@GetMapping("/{id}/tasks")
  @ResponseStatus(HttpStatus.OK)
  public List<Task> getTasks(
      @PathVariable Long id,
      @RequestParam(defaultValue = "0") Integer page) {
    return taskGroupService.groupTasks(id, page);
  }*/

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
    /*if (groupToDelete.getOwner().getId() != appUser.getId()) {
      throw new AppUserNotOwnerException(appUser.getId());
    }*/
    taskGroupService.deleteTaskGroup(id);
  }
}
