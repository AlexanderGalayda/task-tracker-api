package org.example.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.api.controllers.helpers.ControllerHelper;
import org.example.api.dto.AckDto;
import org.example.api.dto.ProjectDto;
import org.example.api.exceptions.BadRequestException;
import org.example.api.factories.ProjectDtoFactory;
import org.example.store.entities.ProjectEntity;
import org.example.store.repositories.ProjectRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Transactional
@RestController
public class ProjectController {

  private final ProjectDtoFactory projectDtoFactory;

  private final ProjectRepository projectRepository;

  private final ControllerHelper controllerHelper;

  public static final String FETCH_PROJECT = "/api/projects";
  public static final String CREATE_PROJECT = "/api/projects";
  public static final String EDIT_PROJECT = "/api/projects/{project_id}";
  public static final String DELETE_PROJECT = "/api/projects/{project_id}";


  @GetMapping(FETCH_PROJECT)
  public List<ProjectDto> fetchProjects(
      @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

    optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

    Stream<ProjectEntity> projectStream = optionalPrefixName
        .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
        .orElseGet(projectRepository::streamAllBy);

    return projectStream
        .map(projectDtoFactory::makeProjectDto)
        .collect(Collectors.toList());
  }

  @PostMapping(CREATE_PROJECT)
  public ProjectDto createProject(@RequestParam String name) {

    if (name.trim().isEmpty()) {
      throw new BadRequestException("Name cannot be empty");
    }
    projectRepository
        .findByName(name)
        .ifPresent(project -> {
          throw new BadRequestException(String.format("Project \"%s\" already exists", name));
        });

    ProjectEntity project = projectRepository.saveAndFlush(
        ProjectEntity.builder()
            .name(name)
            .build()
    );

    return projectDtoFactory.makeProjectDto(project);
  }

  @PatchMapping(EDIT_PROJECT)
  public ProjectDto editProject(@PathVariable("project_id") Long projectId,
      @RequestParam String name) {

    if (name.trim().isEmpty()) {
      throw new BadRequestException("Name cannot be empty");
    }
    ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

    projectRepository
        .findByName(name)
        .filter(anotherProject -> !anotherProject.getId().equals(projectId))
        .ifPresent(projectEntity -> {
          throw new BadRequestException(String.format("Project \"%s\" already exists", name));
        });

    project.setName(name);
    project = projectRepository.saveAndFlush(project);

    return projectDtoFactory.makeProjectDto(project);
  }

  @DeleteMapping(DELETE_PROJECT)
  public AckDto deleteProject(@PathVariable("project_id") Long projectId) {

    controllerHelper.getProjectOrThrowException(projectId);

    projectRepository.deleteById(projectId);

    return AckDto.makeDefault(true);
  }
}
