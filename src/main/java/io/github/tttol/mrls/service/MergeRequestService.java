package io.github.tttol.mrls.service;

import io.github.tttol.mrls.dto.MergeRequestInfoDto;
import io.github.tttol.mrls.external.GitLabApiExecutor;
import io.github.tttol.mrls.form.LinkForm;
import io.github.tttol.mrls.form.MergeRequestInfoForm;
import io.github.tttol.mrls.form.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MergeRequestService {
    private final GitLabApiExecutor gitLabApiExecutor;

    public List<MergeRequestInfoForm> get() {
        var mergeRequestInfoDtos = executeGitLabApi();
        return mergeRequestInfoDtos.stream()
                .collect(Collectors.groupingBy(
                                e -> Objects.isNull(e.getAssignee()) ?
                                        e.getAuthor().getId() : e.getAssignee().getId()
                        )
                )
                .values().stream().map(this::generateForm).toList();
    }

    private List<MergeRequestInfoDto> executeGitLabApi() {
        return gitLabApiExecutor.getMergeRequests();
    }

    private MergeRequestInfoForm generateForm(List<MergeRequestInfoDto> dtos) {
        var userDto = dtos.stream().findAny()
                .map(MergeRequestInfoDto::getAssignee).orElseThrow();
        var userForm = new UserForm(userDto.getId(),
                userDto.getUsername(),
                userDto.getName(),
                userDto.getState(),
                userDto.getAvatarUrl(),
                userDto.getWebUrl());
        var linkForms = dtos.stream().map(e -> new LinkForm(e.getTitle(), e.getWebUrl())).toList();
        return new MergeRequestInfoForm(userForm, linkForms, linkForms.size());
    }
}
