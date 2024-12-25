package io.github.tttol.mrls.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.github.tttol.mrls.dto.GitLabMergeRequestApiResponseDto;
import io.github.tttol.mrls.dto.UserDto;
import io.github.tttol.mrls.external.GitLabApiExecutor;
import io.github.tttol.mrls.form.MrDetailForm;
import io.github.tttol.mrls.form.MrInfoForm;
import io.github.tttol.mrls.form.UserForm;

public class MergeRequestServiceTest {

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @InjectMocks
    private MergeRequestService mergeRequestService;

    @Mock
    private GitLabApiExecutor gitLabApiExecutor;

    @Nested
    class Get {

        @Test
        @DisplayName("GitLabからMRを取得しdto->formの変換ができること")
        void formTest() {
            final var assignee = UserDto.builder()
                    .id(1)
                    .username("assignee_username1")
                    .name("assignee_name1")
                    .state("active")
                    .build();
            final var mergeRequestInfoDtos = List.of(
                    GitLabMergeRequestApiResponseDto.builder()
                            .assignee(assignee)
                            .author(UserDto.builder()
                                    .id(11)
                                    .username("author_username11")
                                    .name("author_name11")
                                    .state("active")
                                    .build())
                            .webUrl("url1")
                            .title("title1")
                            .upvotes(1)
                            .labels(List.of("bugfix"))
                            .createdAt(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .updatedAt(OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .build(),
                    GitLabMergeRequestApiResponseDto.builder()
                            .assignee(assignee)
                            .author(UserDto.builder()
                                    .id(13)
                                    .username("author_username13")
                                    .name("author_name13")
                                    .state("active")
                                    .build())
                            .webUrl("url3")
                            .title("title3")
                            .upvotes(3)
                            .labels(List.of("hotfix"))
                            .createdAt(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .updatedAt(OffsetDateTime.of(2002, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .build(),
                    GitLabMergeRequestApiResponseDto.builder()
                            .assignee(assignee)
                            .author(UserDto.builder()
                                    .id(11)
                                    .username("author_username12")
                                    .name("author_name12")
                                    .state("active")
                                    .build())
                            .webUrl("url2")
                            .title("title2")
                            .upvotes(2)
                            .labels(List.of("modify"))
                            .createdAt(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .updatedAt(OffsetDateTime.of(2001, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .build()

            );
            doReturn(mergeRequestInfoDtos).when(gitLabApiExecutor).getMergeRequests(anyString());
            final var expected = List.of(new MrInfoForm(
                    new UserForm(1, "assignee_username1", "assignee_name1", "active", null, null),
                    List.of(
                            new MrDetailForm(
                                    "title1",
                                    "url1",
                                    new UserForm(
                                            11,
                                            "author_username11",
                                            "author_name11",
                                            "active",
                                            null,
                                            null
                                    ),
                                    1,
                                    List.of("bugfix"),
                                    OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                                    OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00"))
                            ),
                            new MrDetailForm(
                                    "title2",
                                    "url2",
                                    new UserForm(
                                            11,
                                            "author_username12",
                                            "author_name12",
                                            "active",
                                            null,
                                            null
                                    ),
                                    2,
                                    List.of("modify"),
                                    OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                                    OffsetDateTime.of(2001, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00"))
                            ),
                            new MrDetailForm(
                                    "title3",
                                    "url3",
                                    new UserForm(
                                            13,
                                            "author_username13",
                                            "author_name13",
                                            "active",
                                            null,
                                            null
                                    ),
                                    3,
                                    List.of("hotfix"),
                                    OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                                    OffsetDateTime.of(2002, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00"))

                            )
                    ),
                    3
            ));

            final var actual = mergeRequestService.get(anyString());

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        @DisplayName("MRのAssigneeが未指定の場合")
        void noAssigneeTest() {
            final var assignee = UserDto.builder()
                .id(11)
                .username("author_username11")
                .name("author_name11")
                .state("active")
                .build();
            final var mergeRequestInfoDtos = List.of(
                    // authors are same
                    // assignee is null
                    GitLabMergeRequestApiResponseDto.builder()
                            .assignee(null)
                            .author(assignee)
                            .webUrl("url1")
                            .title("title1")
                            .upvotes(0)
                            .labels(List.of())
                            .createdAt(OffsetDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .updatedAt(OffsetDateTime.of(2000, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .build(),
                    // assignee is author_username11 
                    GitLabMergeRequestApiResponseDto.builder()
                            .assignee(assignee)
                            .author(assignee)
                            .webUrl("url2")
                            .title("title2")
                            .upvotes(0)
                            .labels(List.of())
                            .createdAt(OffsetDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .updatedAt(OffsetDateTime.of(2000, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .build()
            );
            doReturn(mergeRequestInfoDtos).when(gitLabApiExecutor).getMergeRequests(anyString());
            final var expected = List.of(
                new MrInfoForm(
                    UserForm.empty(),
                    List.of(
                            new MrDetailForm(
                                    "title1",
                                    "url1",
                                    new UserForm(11, "author_username11", "author_name11", "active", null, null),
                                    0,
                                    List.of(),
                                    OffsetDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")),
                                    OffsetDateTime.of(2000, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00"))
                            )
                    ),
                    1
                ),
                new MrInfoForm(
                    new UserForm(11, "author_username11", "author_name11", "active", null, null),
                    List.of(
                                new MrDetailForm(
                                        "title2",
                                        "url2",
                                        new UserForm(11, "author_username11", "author_name11", "active", null, null),
                                        0,
                                        List.of(),
                                        OffsetDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")),
                                        OffsetDateTime.of(2000, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00"))
                                )
                    ),
                    1
                )
            );

            final var actual = mergeRequestService.get(anyString());

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        @DisplayName("MR0件の場合")
        void noMrTest() {
            doReturn(List.of()).when(gitLabApiExecutor).getMergeRequests(anyString());
            final var expected = List.of();
            final var actual = mergeRequestService.get(anyString());

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }
    }
}
