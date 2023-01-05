package io.github.tttol.mrls.service;

import io.github.tttol.mrls.dto.MergeRequestInfoDto;
import io.github.tttol.mrls.dto.UserDto;
import io.github.tttol.mrls.external.GitLabApiExecutor;
import io.github.tttol.mrls.form.MergeRequestInfoForm;
import io.github.tttol.mrls.form.UserForm;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

public class MergeRequestServiceTest {
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @InjectMocks
    private MergeRequestService mergeRequestService;

    @Mock
    private GitLabApiExecutor gitLabApiExecutor;

    @Nested
    class Get {
        @Test
        @DisplayName("formを生成できること")
        void formTest() {
            var userDto = UserDto.builder()
                    .id(1)
                    .username("username1")
                    .name("name1")
                    .state("active")
                    .build();
            var mergeRequestInfoDtos = List.of(
                    MergeRequestInfoDto.builder()
                            .assignee(userDto)
                            .webUrl("url1")
                            .build(),
                    MergeRequestInfoDto.builder()
                            .assignee(userDto)
                            .webUrl("url2")
                            .build(),
                    MergeRequestInfoDto.builder()
                            .assignee(userDto)
                            .webUrl("url3")
                            .build()
            );
            doReturn(mergeRequestInfoDtos).when(gitLabApiExecutor).getMergeRequests();
            var expected = List.of(new MergeRequestInfoForm(
                    new UserForm(1, "username1", "name1", "active", null, null),
                    List.of("url1", "url2", "url3"),
                    3
            ));

            var actual = mergeRequestService.get();

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }
    }
}
