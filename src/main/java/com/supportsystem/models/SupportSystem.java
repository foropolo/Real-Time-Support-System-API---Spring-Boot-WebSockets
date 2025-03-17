package com.supportsystem.models;

import java.util.List;

// Διεπαφή για το σύστημα υποστήριξης
public interface SupportSystem {
    void submitRequest(Customer customer, String issueDescription);
    void assignRequestToAgent(SupportRequest request, SupportAgent agent);
    List<SupportRequest> getActiveRequests();
}