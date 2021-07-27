package taskTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppUser {

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

  /*@ManyToMany
  private List<TaskGroup> memberOf = new ArrayList<>();*/

  @OneToMany(
      mappedBy = "owner",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY
  )
  @JsonIgnore
  private List<TaskGroup> ownerOf = new ArrayList<>();

  public AppUser(String username, String password) {
    this.username = username;
    this.password = password;
  }

  //TODO: users task groups
}
