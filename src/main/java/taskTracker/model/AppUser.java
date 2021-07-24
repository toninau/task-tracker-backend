package taskTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

  public AppUser(String username, String password) {
    this.username = username;
    this.password = password;
  }

  //TODO: users task groups
}
