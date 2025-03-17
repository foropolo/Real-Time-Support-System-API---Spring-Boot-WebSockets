package com.supportsystem.models;


import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class SupportRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID requestId;

    @ManyToOne
    private Customer customer;

    private String issueDescription;

    @ManyToOne
    private SupportAgent assignedAgent;

    public SupportRequest() {}

    public SupportRequest(Customer customer, String issueDescription) {
        this.customer = customer;
        this.issueDescription = issueDescription;
    }

    public UUID getRequestId() { return requestId; }
    public Customer getCustomer() { return customer; }
    public String getIssueDescription() { return issueDescription; }
    public SupportAgent getAssignedAgent() { return assignedAgent; }

    public void assignAgent(SupportAgent agent) { this.assignedAgent = agent; }
}
