package taskTracker.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

  @Autowired
  private AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return appUserRepository.findByUsername(s)
        .orElseThrow(() ->
            new UsernameNotFoundException(String.format("username: %s, not found", s)));
  }

  public AppUser findUser(Long id) {
    return appUserRepository.findById(id)
        .orElseThrow(() -> new AppUserNotFoundException(id));
  }
}
