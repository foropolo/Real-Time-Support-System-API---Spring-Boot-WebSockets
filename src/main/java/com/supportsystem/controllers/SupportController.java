package com.supportsystem.controllers;

import com.supportsystem.models.*;
import com.supportsystem.repositories.FileAttachmentRepository;
import com.supportsystem.repositories.SupportAgentRepository;
import com.supportsystem.repositories.SupportRequestRepository;
import com.supportsystem.services.SupportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/support")
public class SupportController {
    private final SupportService supportService;
    private final FileAttachmentRepository fileAttachmentRepository;
    private final SupportAgentRepository agentRepository;
    private final SupportRequestRepository requestRepository;


    public SupportController(SupportService supportService,
                             FileAttachmentRepository fileAttachmentRepository,
                             SupportRequestRepository requestRepository,
                             SupportAgentRepository agentRepository) {
        this.supportService = supportService;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.requestRepository = requestRepository;
        this.agentRepository = agentRepository;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitRequest(@RequestBody Customer customer, @RequestParam String issueDescription) {
        UUID requestId = supportService.submitRequest(customer, issueDescription);
        return ResponseEntity.ok("Support request submitted. Request ID: " + requestId);
    }


    @GetMapping("/requests")
    public ResponseEntity<List<SupportRequest>> getActiveRequests() {
        return ResponseEntity.ok(supportService.getActiveRequests());
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignRequestToAgent(@RequestParam UUID requestId) {
        Optional<SupportRequest> requestOpt = requestRepository.findById(requestId);
        Optional<SupportAgent> agentOpt = agentRepository.findAll().stream()
                .filter(SupportAgent::isAvailable)
                .findFirst();

        if (requestOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Support request not found!");
        }

        if (agentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: No available support agents.");
        }

        supportService.assignRequestToAgent(requestOpt.get().getRequestId(), agentOpt.get().getAgentId());
        return ResponseEntity.ok("Request assigned successfully.");
    }



    @PostMapping("/add-agent")
    public ResponseEntity<String> addAgent(@RequestBody SupportAgent agent) {
        supportService.addAgent(agent);
        return ResponseEntity.ok("Agent added successfully.");
    }

    @PostMapping("/message")
    public ResponseEntity<String> sendMessage(
            @RequestParam UUID requestId,
            @RequestParam String sender,
            @RequestParam String content) {

        supportService.saveMessage(requestId, sender, content);
        return ResponseEntity.ok("Message sent and stored.");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam UUID requestId, @RequestParam("file") MultipartFile file) {
        try {
            supportService.saveFile(requestId, file);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/messages/{requestId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable UUID requestId) {
        List<Message> messages = supportService.getMessagesForRequest(requestId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/available-agent")
    public ResponseEntity<SupportAgent> getAvailableAgent() {
        Optional<SupportAgent> agentOpt = supportService.getAvailableAgent();

        return agentOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/attachments/{requestId}")
    public ResponseEntity<List<FileAttachment>> getAttachments(@PathVariable UUID requestId) {
        List<FileAttachment> attachments = fileAttachmentRepository.findBySupportRequestRequestId(requestId);
        return ResponseEntity.ok(attachments);
    }


}
