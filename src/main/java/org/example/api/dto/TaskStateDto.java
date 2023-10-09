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
public class TaskStateDto {

  @NonNull
  private Long id;

  @NonNull
  private String name;

  @NonNull
  private Long ordinal;

  @NonNull
  @JsonProperty("created_at")
  private Instant createdAt;

}
