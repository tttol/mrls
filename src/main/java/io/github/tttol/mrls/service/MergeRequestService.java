package io.github.tttol.mrls.service;

import io.github.tttol.mrls.dto.MergeRequestInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MergeRequestService {
    private final RestTemplate restTemplate;

    @Value("${app.gitlab.api.url}")
    private String apiUrl;

    public String get() {
        return restTemplate.getForObject(apiUrl, String.class);
    }

    public List<MergeRequestInfoDto> getDto() {
        var response = restTemplate.getForObject(apiUrl, MergeRequestInfoDto[].class);
        return Objects.isNull(response) ? List.of() : Arrays.asList(response);
    }
}
