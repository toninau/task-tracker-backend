package taskTracker.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taskTracker.model.TaskGroup;

import java.util.Optional;

@Repository
public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {

  @EntityGraph(value = "TaskGroup.members")
  Optional<TaskGroup> findById(Long aLong);
}
