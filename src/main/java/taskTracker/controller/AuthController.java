package taskTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import taskTracker.model.AppUser;
import taskTracker.model.AppUserDetails;
import taskTracker.util.JwtTokenUtil;
import taskTracker.model.AuthRequest;
import taskTracker.model.AuthResponse;
import taskTracker.service.AuthService;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthResponse register(@RequestBody AuthRequest authRequest) {
    AppUser appUser = authService.registerUser(authRequest);
    AuthResponse authResponse = new AuthResponse(jwtTokenUtil.generateToken(new AppUserDetails(appUser)));
    return authResponse;
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public AuthResponse login(@RequestBody AuthRequest authRequest) {
    AppUser appUser = authService.loginUser(authRequest);
    AuthResponse authResponse = new AuthResponse(jwtTokenUtil.generateToken(new AppUserDetails(appUser)));
    return authResponse;
  }

  @GetMapping("/me")
  @ResponseStatus(HttpStatus.OK)
  public AppUser me(Authentication authentication) {
    AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
    AppUser appUser = userDetails.getAppUser();
    return appUser;
  }
}
