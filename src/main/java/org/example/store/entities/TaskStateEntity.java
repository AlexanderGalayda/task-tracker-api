package org.example.store.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "task_state")
public class TaskStateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String name;

  @OneToOne
  private TaskStateEntity leftTaskState;

  @OneToOne
  private TaskStateEntity rightTaskState;

  @ManyToOne
  private ProjectEntity project;

  @Builder.Default
  private Instant createdAt = Instant.now();

  @Builder.Default
  @OneToMany
  @JoinColumn(name= "task_state_id", referencedColumnName = "id")
  private List<TaskEntity> tasks = new ArrayList<>();

  public Optional<TaskStateEntity> getLeftTaskState() {
    return Optional.ofNullable(leftTaskState);
  }
  public Optional<TaskStateEntity> getRightTaskState() {
    return Optional.ofNullable(rightTaskState);
  }

}
