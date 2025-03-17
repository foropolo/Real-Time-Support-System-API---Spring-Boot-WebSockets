package com.supportsystem.repositories;

import com.supportsystem.models.SupportAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SupportAgentRepository extends JpaRepository<SupportAgent, UUID> {
}
