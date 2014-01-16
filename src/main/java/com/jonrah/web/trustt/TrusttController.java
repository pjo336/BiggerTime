package com.jonrah.web.trustt;

import com.google.gson.Gson;
import com.jonrah.trustt.comment.IssueComment;
import com.jonrah.trustt.comment.service.IssueCommentService;
import com.jonrah.trustt.issue.Issue;
import com.jonrah.trustt.issue.service.IssueService;
import com.jonrah.trustt.type.IssueType;
import com.jonrah.trustt.type.service.IssueTypeService;
import com.jonrah.user.service.UserService;
import com.jonrah.web.trustt.forms.IssueForm;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Peter Johnston on 12/26/13
 * Jonrah
 */

@Controller
public class TrusttController {

    @Autowired
    IssueCommentService issueCommentService;
    @Autowired
    IssueService issueService;
    @Autowired
    IssueTypeService issueTypeService;
    @Autowired
    UserService userService;

    /**
     * Welcome page
     */
    @RequestMapping("/trustt")
    public String getTrustHomePage(ModelMap model) {

        // Find all the issues
        List<Issue> issues = issueService.findAllIssues();

        // List all the possible IssueTypes
        List<IssueType> types = issueTypeService.findAllIssueTypes();
        List<String> issueTypeNames = new ArrayList<String>();
        for(IssueType type: types) {
            issueTypeNames.add(type.getName());
        }

        // Put the issues and the possible types on the model
        model.put("issues", issues);
        model.put("types", issueTypeNames);
        return "trustt-homepage";
    }

    /**
     * Serve the issue creation form.
     */
    @RequestMapping(value = "/trustt/create-issue", method = RequestMethod.GET)
    public String serveIssueForm(@ModelAttribute("issue") IssueForm issueForm, ModelMap model) {

        // List all the possible IssueTypes
        List<IssueType> types = issueTypeService.findAllIssueTypes();
        List<String> issueTypeNames = new ArrayList<String>();
        for(IssueType type: types) {
            issueTypeNames.add(type.getName());
        }

        // Add the type names on the model
        model.put("types", issueTypeNames);

        return "trustt-create-issue";
    }

    /**
     * submit the issue creation form.
     */
    @RequestMapping(value = "/trustt/create-issue", method = RequestMethod.POST)
    public String submitIssueForm(@ModelAttribute("issue") IssueForm issueForm) {
        Issue issue = new Issue();
        issue.setTitle(issueForm.getTitle());
        issue.setDescription(issueForm.getDescription());
        issue.setType(issueTypeService.findIssueByName(issueForm.getType()).get(0));
        issueService.addIssue(issue);
        return "redirect:/trustt/issue/" + issue.getId();
    }

    /**
     * Display the details of a single issue.
     */
    @RequestMapping(value = "/trustt/issue/{issueId}", method = RequestMethod.GET)
    public String serveSingleIssue(@PathVariable long issueId, ModelMap model) throws NotFoundException {

        Issue issue = issueService.restoreIssueById(issueId);
        // Add the issue on the model.
        model.put("issue", issue);

        return "trustt-issue-detail";
    }

    /**
     * Submit a comment to a single issue
     */
    @RequestMapping(value = "/trustt/issue/{issueId}", method = RequestMethod.POST)
    public String submitCommentSingleIssue(@PathVariable long issueId, ModelMap model) throws NotFoundException {
        IssueComment issueComment = new IssueComment();
        issueComment.setBugId(issueService.restoreIssueById(issueId));
        // TODO remove hardcoding
        issueComment.setComment("Herp derp");
        issueComment.setCommenter(userService.restoreUserById(1));
        issueCommentService.addComment(issueComment);


        return "redirect:/trustt/issue/" + issueId;
    }

    /**
     * Display the details of all issues.
     */
    @RequestMapping(value = "/trustt/issues", method = RequestMethod.GET)
    public String serveAllIssues(ModelMap model) throws NotFoundException {

        List<Issue> issues = issueService.findAllIssues();
        // Sort the issues by Date Added ascending
        Collections.sort(issues, new Comparator<Issue>() {
            public int compare(Issue o1, Issue o2) {
                return o2.getDateAdded().compareTo(o1.getDateAdded());
            }
        });
        // TODO do this in service
        List<Issue> openIssues = new ArrayList<Issue>();
        List<Issue> closedIssues = new ArrayList<Issue>();
        for(Issue issue: issues) {
            if(issue.getStatus().getIssueStatusName().equalsIgnoreCase("Open")) {
                openIssues.add(issue);
            } else {
                closedIssues.add(issue);
            }
        }

        // Add the issue on the model.
        model.put("openIssues", openIssues);
        model.put("closedIssues", closedIssues);

        return "trustt-issue-list";
    }

    // TODO pass the issue id and user name into this method
    @RequestMapping(value = "/trustt/updateAssignedUser", method = RequestMethod.POST)
    public void assignUser(ModelMap model, HttpServletResponseWrapper result) {
        System.out.println("Hello");
        model.put("isValid", true);
        model.put("username", "SWING");
        try {
            write(result, model);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void write(HttpServletResponseWrapper result, ModelMap model) throws IOException{
        result.setContentType("application/json");
        result.setCharacterEncoding("UTF-8");
        System.out.println(new Gson().toJson(model));
        result.getWriter().write(new Gson().toJson(model));
    }
}
