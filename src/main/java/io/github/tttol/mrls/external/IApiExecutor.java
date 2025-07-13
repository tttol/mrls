package io.github.tttol.mrls.external;

import java.util.List;

import io.github.tttol.mrls.dto.RequestDto;

public interface IApiExecutor {
    List<RequestDto> getRequests();
}
