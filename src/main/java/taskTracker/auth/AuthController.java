package taskTracker.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import taskTracker.appuser.AppUser;
import taskTracker.jwt.JwtTokenUtil;

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
    AuthResponse authResponse = new AuthResponse(jwtTokenUtil.generateToken(appUser));
    return authResponse;
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public AuthResponse login(@RequestBody AuthRequest authRequest) {
    AppUser appUser = authService.loginUser(authRequest);
    AuthResponse authResponse = new AuthResponse(jwtTokenUtil.generateToken(appUser));
    return authResponse;
  }
}
