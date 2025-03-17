package com.supportsystem.repositories;

import com.supportsystem.models.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, UUID> {
    List<FileAttachment> findBySupportRequestRequestId(UUID requestId);
}
