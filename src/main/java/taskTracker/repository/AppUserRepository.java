package taskTracker.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import taskTracker.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  @EntityGraph(attributePaths = {"ownerOf"})
  Optional<AppUser> findByUsername(String username);
}
