package io.github.tttol.mrls.form;

import java.time.OffsetDateTime;

public record MrDetailForm(String title, String webUrl, UserForm author, OffsetDateTime createdAt,
                           OffsetDateTime updatedAt) {
}
