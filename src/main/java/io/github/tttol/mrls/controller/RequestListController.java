package io.github.tttol.mrls.controller;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.tttol.mrls.presentation.IPresenter;
import io.github.tttol.mrls.service.IRequestService;

@Controller
@RequestMapping("/list")
public class RequestListController {

    private final IRequestService gitlabMergeRequestService;
    private final IPresenter presenter;

    public RequestListController(@Qualifier("gitlabRequestService") final IRequestService gitlabMergeRequestService, @Qualifier("gitlabPresenter") final IPresenter presenter) {
        this.gitlabMergeRequestService = gitlabMergeRequestService;
        this.presenter = presenter;
    }

    @GetMapping("/pat") // Project Access Toekn
    public String pat(final Model model) {
        var requests = gitlabMergeRequestService.getRequests();
        var requestInfoForms = presenter.convert(requests);
        model.addAttribute("requestInfoFormList", requestInfoForms);
        model.addAttribute("oneWeekAgo", OffsetDateTime.now().minusDays(7));
        model.addAttribute("twoWeeksAgo", OffsetDateTime.now().minusDays(14));
        return "list";
    }

    // @GetMapping("/ot") // OAuth 2.0 token
    // public String ot(final Model model) {
    //     model.addAttribute("mrInfoFormList", mergeRequestService.get("dummy"));
    //     return "list";
    // }
}
