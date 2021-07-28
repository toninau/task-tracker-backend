package taskTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Size(min = 3)
  @Column(nullable = false)
  private String text;

  @Column(nullable = false)
  private Boolean finished = false;

  /*@ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private TaskGroup taskGroup;*/

  //TODO: end date, start date, modification date

}
