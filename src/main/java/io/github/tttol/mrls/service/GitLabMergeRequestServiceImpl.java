package io.github.tttol.mrls.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.tttol.mrls.dto.RequestDto;
import io.github.tttol.mrls.external.GitLabApiExecutor;
import io.github.tttol.mrls.external.IApiExecutor;

@Service
public class GitLabMergeRequestServiceImpl implements IRequestService {

        private final IApiExecutor gitLabApiExecutor;
        // private final int NONE_ASSIGNEE = -1;
        public GitLabMergeRequestServiceImpl(final GitLabApiExecutor gitLabApiExecutor) {
                this.gitLabApiExecutor = gitLabApiExecutor;
        }

        @Override
        public List<RequestDto> getRequests() {
                return executeGitLabApi();

                // return mergeRequestInfoDtos.stream()
                // .collect(Collectors.groupingBy(
                // e -> Objects.isNull(e.getAssignee()) ?
                // NONE_ASSIGNEE : e.getAssignee().getId()
                // )
                // )
                // .values().stream().map(this::generateMrInfoForm)
                // .sorted(Comparator.comparing(e -> e.assignee().id()))
                // .toList();
        }

        private List<RequestDto> executeGitLabApi() {
                return gitLabApiExecutor.getRequests();
        }


        // private List<RequestDto> convertToRequestDto(final
        // List<GitLabMergeRequestApiResponseDto> responseDtos) {
        // final var responseDto = responseDtos.stream().findAny().orElseThrow();
        // final var assigneeForm = Optional.ofNullable(responseDto.getAssignee())
        // .map(assignee -> new UserForm(
        // assignee.getId(),
        // assignee.getUsername(),
        // assignee.getName(),
        // assignee.getState(),
        // assignee.getAvatarUrl(),
        // assignee.getWebUrl()
        // )
        // ).orElse(UserForm.empty());
        // final var linkForms = responseDtos.stream()
        // .sorted(Comparator.comparing(GitLabMergeRequestApiResponseDto::getUpdatedAt))
        // .map(e ->
        // new MrDetailForm(
        // e.getTitle(),
        // e.getWebUrl(),
        // Optional.ofNullable(e.getAuthor())
        // .map(assignee -> new UserForm(
        // assignee.getId(),
        // assignee.getUsername(),
        // assignee.getName(),
        // assignee.getState(),
        // assignee.getAvatarUrl(),
        // assignee.getWebUrl()
        // )
        // )
        // .orElse(UserForm.empty()),
        // e.getUpvotes(),
        // e.getLabels(),
        // e.getCreatedAt().withOffsetSameInstant(ZoneOffset.of("+09:00")),
        // e.getUpdatedAt().withOffsetSameInstant(ZoneOffset.of("+09:00"))
        // )).toList();
        // return new MrInfoForm(assigneeForm, linkForms, linkForms.size());
        // }

}
