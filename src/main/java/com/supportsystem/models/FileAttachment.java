package com.supportsystem.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class FileAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID fileId;

    @ManyToOne
    @JoinColumn(name = "support_request_id", nullable = false)
    private SupportRequest supportRequest;

    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;

    public FileAttachment() {}

    public FileAttachment(SupportRequest supportRequest, String fileName, String filePath) {
        this.supportRequest = supportRequest;
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadedAt = LocalDateTime.now();
    }

    public UUID getFileId() { return fileId; }
    public SupportRequest getSupportRequest() { return supportRequest; }
    public String getFileName() { return fileName; }
    public String getFilePath() { return filePath; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
}
