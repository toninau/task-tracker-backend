package taskTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taskTracker.model.AppUser;
import taskTracker.model.AppUserDetails;
import taskTracker.repository.AppUserRepository;
import taskTracker.model.AuthRequest;

import java.util.Optional;

@Service
public class AuthService {

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private AuthenticationProvider authenticationProvider;

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

  @Transactional
  public AppUser loginUser(AuthRequest authRequest) {
    try {
      Authentication auth = authenticationProvider.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
      );
      AppUserDetails userDetails = (AppUserDetails) auth.getPrincipal();
      return userDetails.getAppUser();
    } catch (Exception e) {
      throw new BadCredentialsException(e.getMessage());
    }
  }
}
