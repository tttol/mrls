package io.github.tttol.mrls.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MergeRequestService {
    private final RestTemplate restTemplate;

    @Value("${app.gitlab.api.url}")
    private String apiUrl;

    public String get() {
        return restTemplate.getForObject(apiUrl, String.class);
    }
}
