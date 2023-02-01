package io.github.tttol.mrls.service;

import io.github.tttol.mrls.dto.GitLabMergeRequestApiResponseDto;
import io.github.tttol.mrls.dto.UserDto;
import io.github.tttol.mrls.external.GitLabApiExecutor;
import io.github.tttol.mrls.form.MrDetailForm;
import io.github.tttol.mrls.form.MrInfoForm;
import io.github.tttol.mrls.form.UserForm;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

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
                            .createdAt(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .updatedAt(OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")))
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
                            .createdAt(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .updatedAt(OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")))
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
                                    OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                                    OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00"))
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
                                    OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                                    OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00"))

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
            final var mergeRequestInfoDtos = List.of(
                    GitLabMergeRequestApiResponseDto.builder()
                            .assignee(null)
                            .author(UserDto.builder()
                                    .id(11)
                                    .username("author_username11")
                                    .name("author_name11")
                                    .state("active")
                                    .build())
                            .webUrl("url1")
                            .title("title1")
                            .createdAt(OffsetDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .updatedAt(OffsetDateTime.of(2000, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")))
                            .build()
            );
            doReturn(mergeRequestInfoDtos).when(gitLabApiExecutor).getMergeRequests(anyString());
            final var expected = List.of(new MrInfoForm(
                    UserForm.empty(),
                    List.of(
                            new MrDetailForm(
                                    "title1",
                                    "url1",
                                    new UserForm(11, "author_username11", "author_name11", "active", null, null),
                                    OffsetDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")),
                                    OffsetDateTime.of(2000, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00"))
                            )
                    ),
                    1
            ));

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
