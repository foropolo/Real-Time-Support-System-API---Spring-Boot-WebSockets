package com.supportsystem.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID messageId;

    @ManyToOne
    private SupportRequest supportRequest;

    private String sender; // "customer" or "agent"

    private String content;

    private LocalDateTime timestamp;

    public Message() {}

    public Message(SupportRequest supportRequest, String sender, String content) {
        this.supportRequest = supportRequest;
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getMessageId() { return messageId; }
    public SupportRequest getSupportRequest() { return supportRequest; }
    public String getSender() { return sender; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
}