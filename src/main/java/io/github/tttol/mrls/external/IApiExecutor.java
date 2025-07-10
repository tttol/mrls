package io.github.tttol.mrls.external;

import java.util.List;

import io.github.tttol.mrls.dto.IRequest;

public interface IApiExecutor {
    List<IRequest> getRequests();
}
