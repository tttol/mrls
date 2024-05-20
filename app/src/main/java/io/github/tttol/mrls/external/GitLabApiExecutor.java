package io.github.tttol.mrls.external;

import io.github.tttol.mrls.dto.GitLabMergeRequestApiResponseDto;
import io.github.tttol.mrls.exception.GitLabApiException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class GitLabApiExecutor {

  private final RestTemplate restTemplate;

  @Value("${app.gitlab.api.endpoint}")
  private String endpoint;
  @Value("${app.gitlab.project.accessToken}")
  private String projectAccessToken;

  public List<GitLabMergeRequestApiResponseDto> getMergeRequests(String accessToken) {
    // TODO 例外処理
    try {
      var requestEntity = RequestEntity
          .get(new URI(endpoint))
          .accept(MediaType.APPLICATION_JSON)
          .header("Authorization",
              "Bearer %s".formatted(StringUtils.isNotBlank(accessToken) ?
                  accessToken : projectAccessToken))
          .build();
      log.info("api url -> {}", requestEntity.getUrl());
      var responseEntity = restTemplate.exchange(requestEntity,
          GitLabMergeRequestApiResponseDto[].class);
      log.debug("status code -> {}", responseEntity.getStatusCode());
      var body = responseEntity.getBody();
      return Objects.isNull(body) ? List.of() : Arrays.asList(body);
    } catch (Exception e) {
      throw new GitLabApiException(e);
    }
  }
}
