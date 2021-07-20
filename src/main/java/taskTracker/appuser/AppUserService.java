package taskTracker.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {

  @Autowired
  private AppUserRepository appUserRepository;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return appUserRepository.findByUsername(s)
        .orElseThrow(() ->
            new UsernameNotFoundException(String.format("username: %s, not found", s)));
  }

  @Transactional
  public AppUser registerUser(AppUser appUser) {
    Optional<AppUser> user = appUserRepository.findByUsername(appUser.getUsername());
    if (user.isPresent()) {
      throw new IllegalStateException(String.format("username: %s, is taken", appUser.getUsername()));
    }
    String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
    appUser.setPassword(encodedPassword);
    return appUserRepository.save(appUser);
  }

  @Transactional
  public AppUser loginUser(AppUser appUser) {
    try {
      Authentication auth = authenticationProvider.authenticate(
          new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
      return (AppUser) auth.getPrincipal();
    } catch (Exception e) {
      throw new BadCredentialsException(e.getMessage());
    }
  }
}
