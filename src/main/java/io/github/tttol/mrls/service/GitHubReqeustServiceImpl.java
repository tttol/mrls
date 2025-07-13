package io.github.tttol.mrls.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.tttol.mrls.dto.RequestDto;

@Service("githubRequestService")
public class GitHubReqeustServiceImpl implements IRequestService{

    @Override
    public List<RequestDto> getRequests() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRequests'");
    }

}
