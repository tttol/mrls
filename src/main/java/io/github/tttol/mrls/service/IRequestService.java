package io.github.tttol.mrls.service;

import java.util.List;

import io.github.tttol.mrls.dto.RequestDto;

public interface IRequestService {
    List<RequestDto> getRequests();
}
