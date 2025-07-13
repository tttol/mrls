package io.github.tttol.mrls.form;

import java.util.List;

public record RequestInfoForm(UserForm assignee, List<RequestDetailForm> requestDetailForms, int assignedRequestCount) {
}
