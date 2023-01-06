package io.github.tttol.mrls.form;

import java.util.List;

public record MergeRequestInfoForm(UserForm assignee, List<LinkForm> linkForms, int assignedMrCount) {
}
