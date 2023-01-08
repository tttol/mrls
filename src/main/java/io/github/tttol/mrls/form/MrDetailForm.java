package io.github.tttol.mrls.form;

import java.time.LocalDateTime;

public record MrDetailForm(String title, String webUrl, UserForm author, LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
}
