package org.example.store.repositories;

import java.util.Optional;
import org.example.store.entities.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {

  Optional<TaskStateEntity> findTaskStateEntityByProjectIdAndNameContainsIgnoreCase(Long id,
      String taskStateName);
}
