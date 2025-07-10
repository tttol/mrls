package io.github.tttol.mrls.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.github.tttol.mrls.form.MrInfoForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GitLabMergeRequestApiResponseDto implements IRequest {
    int id;

    int iid;

    int projectId;

    String title;

    String description;

    String state;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime updatedAt;

    String mergedBy;

    Object mergeUser;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime mergedAt;

    String closedBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime closedAt;

    String targetBranch;

    String sourceBranch;

    int userNotesCount;

    int upvotes;

    int downvotes;

    UserDto author;

    List<UserDto> assignees;

    UserDto assignee;

    List<UserDto> reviewers;

    int sourceProjectId;

    int targetProjectId;

    List<String> labels;

    boolean draft;

    boolean workInProgress;

    // TODO ちゃんとする
    Object milestone;

    boolean mergeWhenPipelineSucceeds;

    String mergeStatus;

    String detailedMergeStatus;

    String sha;

    String mergeCommitSha;

    String squashCommitSha;

    String discussionLocked;

    boolean shouldRemoveSourceBranch;

    boolean forceRemoveSourceBranch;

    String reference;

    // TODO ちゃんとする
    Object references;

    String webUrl;

    // TODO ちゃんとする
    Object timeStats;

    boolean squash;

    Object taskCompletionStatus;

    boolean hasConflicts;

    boolean blockingDiscussionsResolved;

    String approvals_before_merge;

    @Override
    public List<MrInfoForm> toForm() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toForm'");
    }
}
