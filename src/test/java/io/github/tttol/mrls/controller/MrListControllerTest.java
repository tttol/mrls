package io.github.tttol.mrls.controller;

import io.github.tttol.mrls.dto.GitLabMergeRequestApiResponseDto;
import io.github.tttol.mrls.dto.UserDto;
import io.github.tttol.mrls.external.GitLabApiExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(properties = {"GITLAB_PROJECT_ID=dummy",
        "GITLAB_ACCESS_TOKEN=dummy"})
public class MrListControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private GitLabApiExecutor gitLabApiExecutor;


    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        var assignee = UserDto.builder()
                .id(1)
                .username("assignee_username1")
                .name("assignee_name1")
                .state("active")
                .build();
        var mergeRequestInfoDtos = List.of(
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
                        .createdAt(LocalDateTime.of(1970, 1, 1, 0, 0, 0))
                        .updatedAt(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
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
                        .createdAt(LocalDateTime.of(1970, 1, 1, 0, 0, 0))
                        .updatedAt(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
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
                        .createdAt(LocalDateTime.of(1970, 1, 1, 0, 0, 0))
                        .updatedAt(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                        .build()
        );
        doReturn(mergeRequestInfoDtos).when(gitLabApiExecutor).getMergeRequests(anyString());
    }

    @Test
    void test() throws Exception {
        mockMvc.perform(get("/list/pat"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"));
    }

}
