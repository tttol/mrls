package io.github.tttol.mrls.service;

import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.tttol.mrls.dto.GitLabMergeRequestApiResponseDto;
import io.github.tttol.mrls.dto.IRequest;
import io.github.tttol.mrls.external.GitLabApiExecutor;
import io.github.tttol.mrls.form.MrDetailForm;
import io.github.tttol.mrls.form.MrInfoForm;
import io.github.tttol.mrls.form.UserForm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabMergeRequestServiceImpl implements IRequestService {

    private final GitLabApiExecutor gitLabApiExecutor;
//     private final int NONE_ASSIGNEE = -1;

    @Override
    public List<IRequest> getRequests() {
        var mergeRequests = executeGitLabApi();
        return mergeRequests.stream().map(mr -> (IRequest) mr).toList();

        // return mergeRequestInfoDtos.stream()
        //         .collect(Collectors.groupingBy(
        //                         e -> Objects.isNull(e.getAssignee()) ?
        //                                 NONE_ASSIGNEE : e.getAssignee().getId()
        //                 )
        //         )
        //         .values().stream().map(this::generateMrInfoForm)
        //         .sorted(Comparator.comparing(e -> e.assignee().id()))
        //         .toList();
    }

    private List<GitLabMergeRequestApiResponseDto> executeGitLabApi() {
        return gitLabApiExecutor.getRequests();
    }

    private MrInfoForm generateMrInfoForm(final List<GitLabMergeRequestApiResponseDto> responseDtos) {
        final var responseDto = responseDtos.stream().findAny().orElseThrow();
        final var assigneeForm = Optional.ofNullable(responseDto.getAssignee())
                .map(assignee -> new UserForm(
                                assignee.getId(),
                                assignee.getUsername(),
                                assignee.getName(),
                                assignee.getState(),
                                assignee.getAvatarUrl(),
                                assignee.getWebUrl()
                        )
                ).orElse(UserForm.empty());
        final var linkForms = responseDtos.stream()
                .sorted(Comparator.comparing(GitLabMergeRequestApiResponseDto::getUpdatedAt))
                .map(e ->
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
                                e.getUpvotes(),
                                e.getLabels(),
                                e.getCreatedAt().withOffsetSameInstant(ZoneOffset.of("+09:00")),
                                e.getUpdatedAt().withOffsetSameInstant(ZoneOffset.of("+09:00"))
                        )).toList();
        return new MrInfoForm(assigneeForm, linkForms, linkForms.size());
    }

}
