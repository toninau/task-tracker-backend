package taskTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import taskTracker.model.Task;
import taskTracker.task.TaskNotFoundException;
import taskTracker.repository.TaskRepository;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  public Task findTask(Long id) {
    return taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));
  }

  public void deleteTask(Long id) {
    try {
      taskRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new TaskNotFoundException(id);
    }
  }
}
