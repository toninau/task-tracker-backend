package taskTracker.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import taskTracker.model.Task;
import taskTracker.model.TaskGroup;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findByTaskGroup(TaskGroup taskGroup, Pageable pageable);

  @Query("SELECT t FROM Task t " +
      "LEFT JOIN FETCH t.taskGroup tg " +
      "LEFT JOIN FETCH tg.members " +
      "WHERE t.id = :id")
  Optional<Task> findTaskWithOwnerAndMembers(@Param("id") Long id);
}
