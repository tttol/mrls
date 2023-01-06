package io.github.tttol.mrls.controller;

import io.github.tttol.mrls.service.MergeRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String list(Model model) {
        var s = mergeRequestService.get();
        return "list";
    }
}
