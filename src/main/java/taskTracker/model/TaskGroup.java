package taskTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NamedEntityGraph(name = "TaskGroup.membersAndOwner",
    attributeNodes = {
        @NamedAttributeNode("members"),
        @NamedAttributeNode("owner")

    })
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

  @ManyToMany(mappedBy = "memberOf", fetch = FetchType.LAZY)
  private Set<AppUser> members = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  private AppUser owner;

  public void setName(String name) {
    this.name = name.trim();
  }

  public void addTask(Task task) {
    this.tasks.add(task);
    task.setTaskGroup(this);
  }

  public void addMember(AppUser appUser) {
    this.members.add(appUser);
    appUser.getMemberOf().add(this);
  }
}
