package io.github.tttol.mrls.form;

import java.time.OffsetDateTime;
import java.util.List;

public record MrDetailForm(String title, String webUrl, UserForm author, int upvotes, List<String> labels,
                           OffsetDateTime createdAt, OffsetDateTime updatedAt) {
}
