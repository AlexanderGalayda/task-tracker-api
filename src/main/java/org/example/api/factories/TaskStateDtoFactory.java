package org.example.api.factories;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.TaskStateDto;
import org.example.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TaskStateDtoFactory {

  TaskDtoFactory taskDtoFactory;

  public TaskStateDto makeTaskStateDto(TaskStateEntity entity) {
    return TaskStateDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .leftTaskStateId(entity.getLeftTaskState().map(TaskStateEntity::getId).orElse(null))
        .rightTaskStateId(entity.getRightTaskState().map(TaskStateEntity::getId).orElse(null))
        .createdAt(entity.getCreatedAt())
        .tasks(entity.getTasks()
            .stream()
            .map(taskDtoFactory::makeTaskDto)
            .collect(Collectors.toList()))
        .build();
  }

}
