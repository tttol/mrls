package io.github.tttol.mrls.presentation;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import io.github.tttol.mrls.dto.RequestDto;
import io.github.tttol.mrls.dto.UserDto;
import io.github.tttol.mrls.form.RequestDetailForm;
import io.github.tttol.mrls.form.RequestInfoForm;
import io.github.tttol.mrls.form.UserForm;

public class GitLabPresenterTest {
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
    private GitLabPresenter presenter;

    @Nested
    class Convert {
        @Test
        @DisplayName("RequestDto->RequestInfoFormの変換ができること")
        void formTest() {
            final var assignee = UserDto.builder()
                    .id(1)
                    .username("assignee_username1")
                    .name("assignee_name1")
                    .state("active")
                    .build();
            final var requests = List.of(
                    new RequestDto(
                            1,
                            "title1",
                            OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                            OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                            1,
                            UserDto.builder()
                                    .id(11)
                                    .username("author_username11")
                                    .name("author_name11")
                                    .state("active")
                                    .build(),
                            assignee,
                            List.of("bugfix"),
                            "url1"
                    ),
                    new RequestDto(
                            2,
                            "title3",
                            OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                            OffsetDateTime.of(2002, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                            3,
                            UserDto.builder()
                                    .id(13)
                                    .username("author_username13")
                                    .name("author_name13")
                                    .state("active")
                                    .build(),
                            assignee,
                            List.of("hotfix"),
                            "url3"
                    ),
                    new RequestDto(
                            3,
                            "title2",
                            OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                            OffsetDateTime.of(2001, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")),
                            2,
                            UserDto.builder()
                                    .id(11)
                                    .username("author_username12")
                                    .name("author_name12")
                                    .state("active")
                                    .build(),
                            assignee,
                            List.of("modify"),
                            "url2"
                    )
            );

            final var expected = List.of(new RequestInfoForm(
                    new UserForm(1, "assignee_username1", "assignee_name1", "active", null, null),
                    List.of(
                            new RequestDetailForm(
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
                            new RequestDetailForm(
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
                            new RequestDetailForm(
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

            final var actual = presenter.convert(requests);

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
            final var requests = List.of(
                    new RequestDto(
                            1,
                            "title1",
                            OffsetDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")),
                            OffsetDateTime.of(2000, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")),
                            0,
                            assignee,
                            null,
                            List.of(),
                            "url1"
                    ),
                    new RequestDto(
                            2,
                            "title2",
                            OffsetDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")),
                            OffsetDateTime.of(2000, 1, 1, 9, 0, 0, 0, ZoneOffset.of("+09:00")),
                            0,
                            assignee,
                            assignee,
                            List.of(),
                            "url2"
                    )
            );

            final var expected = List.of(
                new RequestInfoForm(
                    UserForm.empty(),
                    List.of(
                            new RequestDetailForm(
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
                new RequestInfoForm(
                    new UserForm(11, "author_username11", "author_name11", "active", null, null),
                    List.of(
                                new RequestDetailForm(
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

            final var actual = presenter.convert(requests);

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        @DisplayName("RequestDtoが0件の場合")
        void noMrTest() {
            final var expected = List.of();
            final var actual = presenter.convert(List.of());

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }
    }
}
