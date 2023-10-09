package org.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
  @NonNull
  private Long id;

  @NonNull
  private String name;

  @NonNull
  @JsonProperty("created_at")
  private Instant createdAt;
}
