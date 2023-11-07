package org.example.api.controllers.helpers;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.api.exceptions.NotFoundException;
import org.example.store.entities.ProjectEntity;
import org.example.store.repositories.ProjectRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Transactional
public class ControllerHelper {

  private final ProjectRepository projectRepository;
  public ProjectEntity getProjectOrThrowException(Long projectId) {
    return projectRepository
        .findById(projectId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Project with \"%s\" doesn't exist", projectId)));
  }
}
