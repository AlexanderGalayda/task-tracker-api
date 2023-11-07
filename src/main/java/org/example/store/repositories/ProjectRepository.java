package org.example.store.repositories;

import java.util.Optional;
import java.util.stream.Stream;
import org.example.store.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Optional<ProjectEntity> findByName(String name);
    Stream<ProjectEntity> streamAllBy();
    Stream<ProjectEntity> streamAllByNameStartsWithIgnoreCase(String name);

}
