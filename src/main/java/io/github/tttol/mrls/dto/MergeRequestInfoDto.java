package io.github.tttol.mrls.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MergeRequestInfoDto {
    int id;

    int iid;

    int projectId;

    String title;

    String description;

    String state;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime updatedAt;

    String mergedBy;

    String mergeUser;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime mergedAt;

    String closedBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime closedAt;

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
}
