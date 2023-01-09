package io.github.tttol.mrls.service;

import io.github.tttol.mrls.dto.GitLabMergeRequestApiResponseDto;
import io.github.tttol.mrls.external.GitLabApiExecutor;
import io.github.tttol.mrls.form.MrDetailForm;
import io.github.tttol.mrls.form.MrInfoForm;
import io.github.tttol.mrls.form.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MergeRequestService {
    private final GitLabApiExecutor gitLabApiExecutor;

    public List<MrInfoForm> get() {
        var mergeRequestInfoDtos = executeGitLabApi();
        return mergeRequestInfoDtos.stream()
                .collect(Collectors.groupingBy(
                                e -> Objects.isNull(e.getAssignee()) ?
                                        e.getAuthor().getId() : e.getAssignee().getId()
                        )
                )
                .values().stream().map(this::generateForm)
                .sorted(Comparator.comparing(e -> e.assignee().id()))
                .toList();
    }

    private List<GitLabMergeRequestApiResponseDto> executeGitLabApi() {
        return gitLabApiExecutor.getMergeRequests();
    }

    private MrInfoForm generateForm(List<GitLabMergeRequestApiResponseDto> responseDtos) {
        var responseDto = responseDtos.stream().findAny().orElseThrow();
        var assigneeForm = Optional.ofNullable(responseDto.getAssignee())
                .map(assignee -> new UserForm(
                                assignee.getId(),
                                assignee.getUsername(),
                                assignee.getName(),
                                assignee.getState(),
                                assignee.getAvatarUrl(),
                                assignee.getWebUrl()
                        )
                ).orElse(UserForm.empty());
        var linkForms = responseDtos.stream().map(e ->
                new MrDetailForm(
                        e.getTitle(),
                        e.getWebUrl(),
                        Optional.ofNullable(e.getAuthor())
                                .map(assignee -> new UserForm(
                                                assignee.getId(),
                                                assignee.getUsername(),
                                                assignee.getName(),
                                                assignee.getState(),
                                                assignee.getAvatarUrl(),
                                                assignee.getWebUrl()
                                        )
                                )
                                .orElse(UserForm.empty()),
                        toJst(e.getCreatedAt()),
                        toJst(e.getUpdatedAt())
                )).toList();
        return new MrInfoForm(assigneeForm, linkForms, linkForms.size());
    }

    private LocalDateTime toJst(LocalDateTime utc) {
        return ZonedDateTime.of(utc, ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Tokyo")).toLocalDateTime();
    }
}
