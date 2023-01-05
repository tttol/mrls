package io.github.tttol.mrls.external;

import io.github.tttol.mrls.dto.MergeRequestInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GitLabApiExecutor {
    private final RestTemplate restTemplate;

    @Value("${app.gitlab.api.endpoint}")
    private String endpoint;

    public List<MergeRequestInfoDto> getMergeRequests() {
        // TODO 例外処理
        var response = restTemplate.getForObject(endpoint, MergeRequestInfoDto[].class);
        return Objects.isNull(response) ? List.of() : Arrays.asList(response);
    }
}
