package taskTracker.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taskTracker.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findByUsername(String username);

  @EntityGraph(attributePaths = {"memberOf"})
  @Query("select a from AppUser a where a.id = ?1")
  Optional<AppUser> findAppUserById(Long aLong);
}
