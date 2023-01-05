package io.github.tttol.mrls.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Value("${app.gitlab.uri}")
    private String gitlabUri;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri(gitlabUri).build();
    }
}
