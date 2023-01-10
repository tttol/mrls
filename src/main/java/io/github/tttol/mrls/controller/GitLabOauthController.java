package io.github.tttol.mrls.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/gitlab/oauth")
public class GitLabOauthController {
    @GetMapping("/authorize")
    public String oauth() {
        return "";
    }
}
