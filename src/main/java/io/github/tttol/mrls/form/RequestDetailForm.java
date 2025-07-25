package io.github.tttol.mrls.form;

import java.time.OffsetDateTime;
import java.util.List;

public record RequestDetailForm(String title, String url, UserForm author, int upvotes, List<String> labels,
                           OffsetDateTime createdAt, OffsetDateTime updatedAt) {
}
