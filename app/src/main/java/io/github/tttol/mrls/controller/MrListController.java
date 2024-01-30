package io.github.tttol.mrls.controller;

import java.time.OffsetDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.tttol.mrls.service.MergeRequestService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/list")
@RequiredArgsConstructor
public class MrListController {

    private final MergeRequestService mergeRequestService;

    @GetMapping("/pat") // Project Access Toekn
    public String pat(final Model model) {
        model.addAttribute("mrInfoFormList", mergeRequestService.get(StringUtils.EMPTY));
        model.addAttribute("oneWeekAgo", OffsetDateTime.now().minusDays(7));
        model.addAttribute("twoWeeksAgo", OffsetDateTime.now().minusDays(14));
        return "list";
    }

    @GetMapping("/ot") // OAuth 2.0 token
    public String ot(final Model model) {
        model.addAttribute("mrInfoFormList", mergeRequestService.get("dummy"));
        return "list";
    }
}
