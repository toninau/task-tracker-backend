package taskTracker.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taskTracker.appuser.AppUser;
import taskTracker.appuser.AppUserRepository;

import java.util.Optional;

@Service
public class AuthService {

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;


  @Transactional
  public AppUser registerUser(AuthRequest authRequest) {
    Optional<AppUser> appUser = appUserRepository.findByUsername(authRequest.getUsername());
    if (appUser.isPresent()) {
      throw new IllegalStateException(String.format("username: %s, is taken", authRequest.getUsername()));
    }
    String encodedPassword = bCryptPasswordEncoder.encode(authRequest.getPassword());
    AppUser appUserToCreate = new AppUser(authRequest.getUsername(), encodedPassword);
    return appUserRepository.save(appUserToCreate);
  }


}
