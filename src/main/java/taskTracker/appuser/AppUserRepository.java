package taskTracker.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import taskTracker.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  Optional<AppUser> findByUsername(String username);
}
