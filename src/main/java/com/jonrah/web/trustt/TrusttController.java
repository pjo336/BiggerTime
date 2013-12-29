package com.jonrah.web.trustt;

import com.jonrah.trustt.issue.Issue;
import com.jonrah.trustt.issue.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * Created by pjo336 on 12/26/13
 * biggertime
 */

@Controller
public class TrusttController {

    @Autowired
    IssueService issueService;

    @RequestMapping("/trustt")
    public ModelAndView getTrustHomePage(@ModelAttribute("issue") Issue issue, BindingResult result) {

        return new ModelAndView("trustt/trustt-homepage", "model", new HashMap<String, Object>());
    }

    @RequestMapping("/createIssue")
    public ModelAndView createIssue(@ModelAttribute("issue") Issue issue, BindingResult result) {
        issueService.addIssue(issue);
        System.out.println("Issue titled : " + issue.getTitle() + " was added to the database");
        return new ModelAndView("redirect:/trustt.html");
    }
}
