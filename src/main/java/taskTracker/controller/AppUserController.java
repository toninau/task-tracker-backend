package taskTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import taskTracker.exception.NoAccessToUserException;
import taskTracker.model.AppUser;
import taskTracker.model.AppUserDetails;
import taskTracker.model.TaskGroup;
import taskTracker.service.AppUserService;
import taskTracker.service.TaskGroupService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @Autowired
  private TaskGroupService taskGroupService;

  @GetMapping("/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public AppUser getUser(@PathVariable("userId") Long id, Authentication authentication) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    if (appUser.getId() != id) {
      throw new NoAccessToUserException(appUser.getId());
    }
    return appUser;
  }

  //TODO: fetching user information, users groups
  @GetMapping("/{userId}/groups")
  @ResponseStatus(HttpStatus.OK)
  public List<TaskGroup> getTaskGroups(@PathVariable("userId") Long id, Authentication authentication) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    if (appUser.getId() != id) {
      throw new NoAccessToUserException(appUser.getId());
    }
    List<TaskGroup> taskGroups = taskGroupService.getUserTaskGroups(appUser);
    return taskGroups;
  }
}
