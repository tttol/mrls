package io.github.tttol.mrls.controller;

import io.github.tttol.mrls.service.MergeRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/list")
@RequiredArgsConstructor
@Slf4j
public class MrListController {
    private final MergeRequestService mergeRequestService;

    @GetMapping
    public String list(Model model,
                       @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oAuth2AuthorizedClient,
                       @AuthenticationPrincipal OAuth2User oAuth2User) {
        model.addAttribute("mrInfoFormList", mergeRequestService.get());
        model.addAttribute("userName", oAuth2User.getName());
        model.addAttribute("clientName", oAuth2AuthorizedClient.getClientRegistration().getClientName());
        model.addAttribute("userAttributes", oAuth2User.getAttributes());
        return "list";
    }
}
