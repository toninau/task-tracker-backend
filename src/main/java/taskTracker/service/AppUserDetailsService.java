package taskTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import taskTracker.appuser.AppUserRepository;
import taskTracker.model.AppUser;
import taskTracker.model.AppUserDetails;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

  @Autowired
  private AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    Optional<AppUser> appUser = appUserRepository.findByUsername(s);
    if (!appUser.isPresent()) {
      throw new UsernameNotFoundException(String.format("username: %s, not found", s));
    }
    return new AppUserDetails(appUser.get());
  }
}
