package taskTracker.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taskTracker.model.Task;
import taskTracker.model.TaskGroup;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  /*
  @Query(value = "SELECT * FROM task WHERE task_group_id = ?1", nativeQuery = true)
  List<Task> findByTaskGroup(Long id);
  */

  /*List<Task> findByTaskGroup(TaskGroup taskGroup, Pageable pageable);*/
}
