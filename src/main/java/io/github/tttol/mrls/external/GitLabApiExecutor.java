package io.github.tttol.mrls.external;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import io.github.tttol.mrls.dto.GitLabMergeRequestApiResponseDto;
import io.github.tttol.mrls.dto.IRequest;
import io.github.tttol.mrls.exception.GitLabApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GitLabApiExecutor implements IApiExecutor {

  private final RestClient restClient;

  @Value("${app.gitlab.api.endpoint}")
  private String endpoint;
  @Value("${app.gitlab.project.accessToken}")
  private String projectAccessToken;

  @Override
  public List<IRequest> getRequests() {
    try {
      if (StringUtils.isBlank(projectAccessToken)) {
        throw new GitLabApiException("projectAccessToken must not be blank.");
      }
      var token = projectAccessToken;
      var responseEntity = restClient.get()
          .uri(new URI(endpoint))
          .accept(MediaType.APPLICATION_JSON)
          .header("PRIVATE-TOKEN", token)
          .retrieve()
          .toEntity(GitLabMergeRequestApiResponseDto[].class);

      if (responseEntity != null) {
        log.info("status code -> {}", responseEntity.getStatusCode());
        var body = responseEntity.getBody();
        return Objects.isNull(body) ? List.of() : Arrays.asList(body);
      }
      return List.of();
    } catch (Exception e) {
      throw new GitLabApiException(e);
    }
  }
}
