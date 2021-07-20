package taskTracker.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import taskTracker.jwt.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/users")
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public String register(@RequestBody AppUser user) {
    AppUser appUser = appUserService.registerUser(user);
    return jwtTokenUtil.generateToken(appUser);
  }

  @PostMapping("/login")
  public String login(@RequestBody AppUser user) {
    return jwtTokenUtil.generateToken(appUserService.loginUser(user));
  }

  @GetMapping("/me")
  public Map<String, Object> me(Authentication auth) {
    AppUser user = (AppUser) auth.getPrincipal();
    Map<String, Object> fields = new HashMap<>();
    fields.put("id", user.getId());
    fields.put("username", user.getUsername());
    return fields;
  }

  //TODO: fetching user information, users groups

  //TODO: Adding users to group
}
