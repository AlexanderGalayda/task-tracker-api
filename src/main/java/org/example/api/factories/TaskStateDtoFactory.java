package org.example.api.factories;

import org.example.api.dto.TaskStateDto;
import org.example.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateDtoFactory {

  public TaskStateDto makeTaskStateDto(TaskStateEntity entity) {
    return TaskStateDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .ordinal(entity.getOrdinal())
        .createdAt(entity.getCreatedAt())
        .build();
  }

}
