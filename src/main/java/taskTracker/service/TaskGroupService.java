package taskTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taskTracker.model.Task;
import taskTracker.model.TaskGroup;
import taskTracker.repository.TaskRepository;
import taskTracker.taskgroup.TaskGroupNotFoundException;
import taskTracker.repository.TaskGroupRepository;

import java.util.List;

@Service
public class TaskGroupService {

  @Autowired
  private TaskGroupRepository taskGroupRepository;
  @Autowired
  private TaskRepository taskRepository;

  public TaskGroup createGroup(TaskGroup taskGroup) {
    return taskGroupRepository.save(taskGroup);
  }

  @Transactional
  public Task createTask(Long id, Task task) {
    TaskGroup group = taskGroupRepository.findById(id)
        .orElseThrow(() -> new TaskGroupNotFoundException(id));
    group.addTask(task);
    return task;
  }

  @Transactional
  public List<Task> groupTasks(Long id, Integer page) {
    TaskGroup group = taskGroupRepository.findById(id)
        .orElseThrow(() -> new TaskGroupNotFoundException(id));
    Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
    return taskRepository.findByTaskGroup(group, pageable);
  }

  public void deleteTaskGroup(Long id) {
    try {
      taskGroupRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new TaskGroupNotFoundException(id);
    }
  }

  public TaskGroup getTaskGroup(Long id) {
    return taskGroupRepository.findById(id)
        .orElseThrow(() -> new TaskGroupNotFoundException(id));
  }
}
