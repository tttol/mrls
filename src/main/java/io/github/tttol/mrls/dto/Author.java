package io.github.tttol.mrls.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Author {
    int id;

    String username;

    String name;

    String state;

    String avatarUrl;

    String webUrl;
}
