package com.supportsystem.repositories;


import com.supportsystem.models.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SupportRequestRepository extends JpaRepository<SupportRequest, UUID> {
}
