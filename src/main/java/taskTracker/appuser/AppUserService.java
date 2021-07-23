package taskTracker.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

  @Autowired
  private AppUserRepository appUserRepository;

  public AppUser findUser(Long id) {
    return appUserRepository.findById(id)
        .orElseThrow(() -> new AppUserNotFoundException(id));
  }
}
