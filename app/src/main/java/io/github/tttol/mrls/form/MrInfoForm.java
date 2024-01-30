package io.github.tttol.mrls.form;

import java.util.List;

public record MrInfoForm(UserForm assignee, List<MrDetailForm> mrDetailForms, int assignedMrCount) {
}
