package io.github.tttol.mrls.controller;

import io.github.tttol.mrls.dto.RequestDto;
import io.github.tttol.mrls.form.RequestDetailForm;
import io.github.tttol.mrls.form.RequestInfoForm;
import io.github.tttol.mrls.form.UserForm;
import io.github.tttol.mrls.presentation.IPresenter;
import io.github.tttol.mrls.service.IRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(properties = {
        "PROXY_HOST=dummy.com",
        "PROXY_PORT=80",
        "GITLAB_PROJECT_ID=dummy",
        "GITLAB_ACCESS_TOKEN=dummy"})
public class RequestListControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockitoBean
    @Qualifier("gitlabRequestService")
    private IRequestService requestService;
    @MockitoBean
    @Qualifier("gitlabPresenter")
    private IPresenter presenter;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        var dummyUser = new UserForm(1, "test-user", "Test User", "active", "https://example.com/avatar.png", "https://example.com/user");
        var dummyRequestDetail = new RequestDetailForm(
            "Test Request",
            "https://example.com/request",
            dummyUser,
            0,
            List.of("test"),
            OffsetDateTime.now(),
            OffsetDateTime.now()
        );
        doReturn(List.of(new RequestDto(1, null, null, null, 0, null, null, null, null))).when(requestService).getRequests();
        doReturn(List.of(new RequestInfoForm(dummyUser, List.of(dummyRequestDetail), 1))).when(presenter).convert(any());
    }

    @Test
    void test() throws Exception {
        mockMvc.perform(get("/list/pat"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"));
    }
}
