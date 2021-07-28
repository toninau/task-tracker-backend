package taskTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Data
public class AppUser implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Size(min = 3, max = 12)
  @Column(nullable = false)
  private String username;

  @NotEmpty
  @Column(nullable = false)
  @JsonIgnore
  private String password;

  @ManyToMany
  @JoinTable(name = "task_group_members",
      joinColumns = @JoinColumn(name = "app_user_id"),
      inverseJoinColumns = @JoinColumn(name = "task_group_id")
  )
  @JsonIgnore
  private Set<TaskGroup> memberOf = new HashSet<>();

  /*@OneToMany(
      mappedBy = "owner",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY
  )
  @JsonIgnore
  private List<TaskGroup> ownerOf = new ArrayList<>();*/

  public AppUser(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Set<TaskGroup> getMemberOf() {
    return this.memberOf;
  }

  public String getPassword() {
    return this.password;
  }

  public String getUsername() {
    return this.username;
  }

  //TODO: users task groups
}
