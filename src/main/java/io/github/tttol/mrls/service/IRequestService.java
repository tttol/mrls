package io.github.tttol.mrls.service;

import java.util.List;

import io.github.tttol.mrls.dto.IRequest;

public interface IRequestService {
    List<IRequest> getRequests();
}
