package io.github.tttol.mrls.controller;

import io.github.tttol.mrls.service.MergeRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/list")
@RequiredArgsConstructor
public class MrListController {
    private final MergeRequestService mergeRequestService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("obj", mergeRequestService.get());
        return "list";
    }
}
