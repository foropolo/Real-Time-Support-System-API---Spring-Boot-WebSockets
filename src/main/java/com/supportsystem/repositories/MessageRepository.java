package com.supportsystem.repositories;

import com.supportsystem.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findBySupportRequestRequestIdOrderByTimestampAsc(UUID requestId);
}
