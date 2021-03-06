package taskTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskTracker.exception.AppUserNotFoundException;
import taskTracker.repository.AppUserRepository;
import taskTracker.model.AppUser;

@Service
public class AppUserService {

  @Autowired
  private AppUserRepository appUserRepository;

  public AppUser findUser(Long id) {
    return appUserRepository.findAppUserWithMemberOwnerById(id)
        .orElseThrow(() -> new AppUserNotFoundException(id));
  }

  public void updateUser(AppUser appUser) {
    appUserRepository.save(appUser);
  }
}
