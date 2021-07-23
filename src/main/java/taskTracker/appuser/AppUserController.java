package taskTracker.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @GetMapping("/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public AppUser getUser(@PathVariable("userId") Long id) {
    return appUserService.findUser(id);
  }

  //TODO: fetching user information, users groups

  //TODO: Adding users to group
}
