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
import io.github.tttol.mrls.dto.RequestDto;
import io.github.tttol.mrls.exception.GitLabApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component("gitlabApiExecutor")
@RequiredArgsConstructor
@Slf4j
public class GitLabApiExecutor implements IApiExecutor {

  private final RestClient restClient;

  @Value("${app.gitlab.api.endpoint}")
  private String endpoint;
  @Value("${app.gitlab.project.accessToken}")
  private String projectAccessToken;

  @Override
  public List<RequestDto> getRequests() {
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

      log.info("status code -> {}", responseEntity.getStatusCode());
      var body = responseEntity.getBody();
      return Objects.isNull(body) ? List.of() : convertToRequestDto(Arrays.asList(body));
    } catch (Exception e) {
      throw new GitLabApiException(e);
    }
  }

  /**
   * Parsing into a list of {@link GitLabMergeRequestApiResponseDto} and then mapped to {@link RequestDto}.
   *
   * @return a list of {@link RequestDto}
   */
  private List<RequestDto> convertToRequestDto(final List<GitLabMergeRequestApiResponseDto> responseDtos) {
    return responseDtos.stream()
        .map(r -> new RequestDto(
            r.getId(),
            r.getTitle(),
            r.getCreatedAt(),
            r.getUpdatedAt(),
            r.getUpvotes(),
            r.getAuthor(),
            r.getAssignee(),
            r.getLabels(),
            r.getWebUrl()))
        .toList();
  }
}
