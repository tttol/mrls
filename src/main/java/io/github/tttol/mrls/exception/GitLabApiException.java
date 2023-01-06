package io.github.tttol.mrls.exception;

public class GitLabApiException extends RuntimeException {
    public GitLabApiException(Throwable e) {
        super(e);
    }
}
