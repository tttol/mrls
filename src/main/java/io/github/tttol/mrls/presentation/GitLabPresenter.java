package io.github.tttol.mrls.presentation;

import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.github.tttol.mrls.dto.RequestDto;
import io.github.tttol.mrls.form.RequestDetailForm;
import io.github.tttol.mrls.form.RequestInfoForm;
import io.github.tttol.mrls.form.UserForm;

@Component
public class GitLabPresenter implements IPresenter {
  private final int NONE_ASSIGNEE = -1;

  @Override
  public List<RequestInfoForm> convert(List<RequestDto> requests) {
    return requests.stream()
        .collect(Collectors.groupingBy(
            e -> Objects.isNull(e.assignee()) ? NONE_ASSIGNEE : e.assignee().getId()))
        .values().stream().map(this::generateRequestInfoForm)
        .sorted(Comparator.comparing(e -> e.assignee().id()))
        .toList();
  }

  private RequestInfoForm generateRequestInfoForm(List<RequestDto> requests) {
    final var anyRequest = requests.stream().findAny().orElseThrow();
    final var assigneeForm = Optional.ofNullable(anyRequest.assignee())
        .map(assignee -> new UserForm(
            assignee.getId(),
            assignee.getUsername(),
            assignee.getName(),
            assignee.getState(),
            assignee.getAvatarUrl(),
            assignee.getWebUrl()))
        .orElse(UserForm.empty());
    final var requestDetailForms = requests.stream()
        .sorted(Comparator.comparing(r -> r.updatedAt()))
        .map(r -> new RequestDetailForm(
            r.title(),
            r.url(),
            Optional.ofNullable(r.author())
                .map(assignee -> new UserForm(
                    assignee.getId(),
                    assignee.getUsername(),
                    assignee.getName(),
                    assignee.getState(),
                    assignee.getAvatarUrl(),
                    assignee.getWebUrl()))
                .orElse(UserForm.empty()),
            r.upvotes(),
            r.labels(),
            r.createdAt().withOffsetSameInstant(ZoneOffset.of("+09:00")),
            r.updatedAt().withOffsetSameInstant(ZoneOffset.of("+09:00"))))
        .toList();
    return new RequestInfoForm(assigneeForm, requestDetailForms, requestDetailForms.size());
  }

}
