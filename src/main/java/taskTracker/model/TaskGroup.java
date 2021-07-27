package taskTracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskGroup implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Size(min = 3, max = 12)
  @Column(nullable = false)
  private String name;

  @OneToMany(
      mappedBy = "taskGroup",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY
  )
  @JsonIgnore
  private List<Task> tasks = new ArrayList<>();

  /*@ManyToMany(mappedBy = "memberOf")
  private List<AppUser> members = new ArrayList<>();*/

  @ManyToOne(fetch = FetchType.EAGER)
  private AppUser owner;

  public void setName(String name) {
    this.name = name.trim();
  }

  public void addTask(Task task) {
    this.tasks.add(task);
    task.setTaskGroup(this);
  }
}
