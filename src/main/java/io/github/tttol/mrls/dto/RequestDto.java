package io.github.tttol.mrls.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record RequestDto(int id, String title, OffsetDateTime createdAt, OffsetDateTime updatedAt, int upvotes,
        UserDto author, UserDto assignee, List<String> labels, String url) {
}
