package taskTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import taskTracker.exception.NoAccessToUserException;
import taskTracker.model.AppUser;
import taskTracker.model.AppUserDetails;
import taskTracker.model.TaskGroup;
import taskTracker.service.AppUserService;
import taskTracker.service.TaskGroupService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/users")
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @Autowired
  private TaskGroupService taskGroupService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

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

  //change password
  @PutMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateUser(
      @RequestBody Map<String, String> requestBody,
      @PathVariable("userId") Long id,
      Authentication authentication
  ) {
    AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = appUserDetails.getAppUser();
    if (appUser.getId() != id) {
      throw new NoAccessToUserException(appUser.getId());
    }
    String password = requestBody.get("password");
    if (password != null) {
      String encodedPassword = bCryptPasswordEncoder.encode(password);
      appUser.setPassword(encodedPassword);
      appUserService.updateUser(appUser);
    }
  }
}
