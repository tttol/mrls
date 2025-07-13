package io.github.tttol.mrls.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.github.tttol.mrls.dto.RequestDto;
import io.github.tttol.mrls.external.IApiExecutor;

@Service("gitlabRequestService")
public class GitLabRequestServiceImpl implements IRequestService {

        private final IApiExecutor gitLabApiExecutor;
        public GitLabRequestServiceImpl(@Qualifier("gitlabApiExecutor") final IApiExecutor gitLabApiExecutor) {
                this.gitLabApiExecutor = gitLabApiExecutor;
        }

        @Override
        public List<RequestDto> getRequests() {
                return executeGitLabApi();
        }

        private List<RequestDto> executeGitLabApi() {
                return gitLabApiExecutor.getRequests();
        }
}
