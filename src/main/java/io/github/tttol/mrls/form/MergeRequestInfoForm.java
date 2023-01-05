package io.github.tttol.mrls.form;

import java.util.List;

public record MergeRequestInfoForm(UserForm assignee, List<String> webUrls, int assignedMrCount) {
}
