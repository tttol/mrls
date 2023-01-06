package io.github.tttol.mrls.handler;

import io.github.tttol.mrls.exception.GitLabApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(GitLabApiException.class)
    public String handler(GitLabApiException e, Model model) {
        log.error("exception = ", e);
        model.addAttribute("message", e.getMessage());
        return "apiError";
    }
}
