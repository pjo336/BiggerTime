package com.pjo.jonrah.trustt.issue;

import com.pjo.jonrah.entity.JonrahEntity;
import com.pjo.jonrah.trustt.comment.IssueComment;
import com.pjo.jonrah.trustt.milestone.Milestone;
import com.pjo.jonrah.trustt.type.IssueType;
import com.pjo.jonrah.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Peter Johnston on 12/23/13
 * Jonrah
 */

@Entity
@Table(name = "JONRAH_ISSUE")
public class Issue implements JonrahEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name="created_by_id")
    private User createdById;

    @ManyToOne
    @JoinColumn(name="last_modified_by_id")
    private User lastModifiedById;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private User ownerId;

    @ManyToOne
    @JoinColumn(name="assigned_to_id")
    private User assignedToId;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestoneId;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "type")
    private IssueType type;

    @Column(name = "priority")
    private IssuePriority priority;

    @Column(name = "due_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @Column(name = "date_added")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;

    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

    /**
     * Comments on this issue
     */
    @OneToMany(mappedBy="bug")
    private Set<IssueComment> comments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getCreatedById() {
        return createdById;
    }

    public void setCreatedById(User createdById) {
        this.createdById = createdById;
    }

    public User getLastModifiedById() {
        return lastModifiedById;
    }

    public void setLastModifiedById(User lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }

    public User getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(User assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Milestone getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Milestone milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IssueStatus getStatus() {
        return IssueStatus.getIssueStatus(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public IssuePriority getPriority() {
        return this.priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
}
