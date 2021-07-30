package taskTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taskTracker.model.AppUser;
import taskTracker.model.Task;
import taskTracker.model.TaskGroup;
import taskTracker.repository.TaskRepository;
import taskTracker.exception.TaskGroupNotFoundException;
import taskTracker.repository.TaskGroupRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskGroupService {

  @Autowired
  private TaskGroupRepository taskGroupRepository;
  @Autowired
  private TaskRepository taskRepository;

  public TaskGroup createGroup(TaskGroup taskGroup) {
    return taskGroupRepository.save(taskGroup);
  }

  public TaskGroup updateGroup(TaskGroup taskGroup) {
    return taskGroupRepository.save(taskGroup);
  }

  public List<Task> groupTasks(TaskGroup taskGroup, Integer page) {
    Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
    return taskRepository.findByTaskGroup(taskGroup, pageable);
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

  @Transactional
  public List<TaskGroup> getUserTaskGroups(AppUser appUser) {
    List<TaskGroup> groupsByOwner = taskGroupRepository.findByOwner(appUser);
    List<TaskGroup> groupsByMember = taskGroupRepository.findByMembers(appUser);
    List<TaskGroup> newList = Stream.concat(groupsByOwner.stream(), groupsByMember.stream()).collect(Collectors.toList());
    return newList;
  }
}
