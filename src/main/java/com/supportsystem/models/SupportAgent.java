package com.supportsystem.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class SupportAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID agentId;

    private String name;

    private boolean available = true; // Default είναι διαθέσιμος

    public SupportAgent() {}

    public SupportAgent(String name) {
        this.name = name;
        this.available = true;
    }

    public UUID getAgentId() { return agentId; }
    public String getName() { return name; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }
}
