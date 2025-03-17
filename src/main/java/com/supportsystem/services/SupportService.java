package com.supportsystem.services;

import com.supportsystem.models.*;
import com.supportsystem.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class SupportService {
    private final SupportRequestRepository requestRepository;
    private final SupportAgentRepository agentRepository;
    private final CustomerRepository customerRepository;
    private final MessageRepository messageRepository;
    private final FileAttachmentRepository fileAttachmentRepository;

    public SupportService(SupportRequestRepository requestRepository,
                          SupportAgentRepository agentRepository,
                          CustomerRepository customerRepository,
                          MessageRepository messageRepository,
                          FileAttachmentRepository fileAttachmentRepository) {

        this.requestRepository = requestRepository;
        this.agentRepository = agentRepository;
        this.customerRepository = customerRepository;
        this.messageRepository = messageRepository;
        this.fileAttachmentRepository = fileAttachmentRepository;

    }

    public UUID submitRequest(Customer customer, String issueDescription) {
        customerRepository.save(customer);
        SupportRequest request = new SupportRequest(customer, issueDescription);
        requestRepository.save(request);
        return request.getRequestId();
    }


    public List<SupportRequest> getActiveRequests() {
        return requestRepository.findAll();
    }

    public void assignRequestToAgent(UUID requestId, UUID agentId) {
        Optional<SupportRequest> requestOpt = requestRepository.findById(requestId);
        Optional<SupportAgent> agentOpt = agentRepository.findById(agentId);

        if (requestOpt.isPresent() && agentOpt.isPresent()) {
            SupportRequest request = requestOpt.get();
            SupportAgent agent = agentOpt.get();

            if (!agent.isAvailable()) {
                throw new IllegalStateException("Agent is not available!");
            }

            request.assignAgent(agent);
            agent.setAvailable(false); // Τον κάνουμε μη διαθέσιμο

            requestRepository.save(request);
            agentRepository.save(agent);
        } else {
            throw new IllegalArgumentException("Request or agent not found.");
        }
    }


    public void addAgent(SupportAgent agent) {
        agentRepository.save(agent);
    }

    public Optional<SupportAgent> getAvailableAgent() {
        return agentRepository.findAll().stream()
                .filter(SupportAgent::isAvailable)
                .findFirst();
    }


    public void saveMessage(UUID requestId, String sender, String content) {
        Optional<SupportRequest> supportRequestOpt = requestRepository.findById(requestId);

        if (supportRequestOpt.isPresent()) {
            Message message = new Message(supportRequestOpt.get(), sender, content);
            messageRepository.save(message);
        } else {
            throw new IllegalArgumentException("Support request not found!");
        }
    }

    public List<Message> getMessagesForRequest(UUID requestId) {
        return messageRepository.findBySupportRequestRequestIdOrderByTimestampAsc(requestId);
    }

    public void saveFile(UUID requestId, MultipartFile file) throws Exception {
        Optional<SupportRequest> supportRequestOpt = requestRepository.findById(requestId);

        if (supportRequestOpt.isEmpty()) {
            throw new IllegalArgumentException("Support request not found!");
        }

        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir));

        String filePath = uploadDir + file.getOriginalFilename();
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        FileAttachment attachment = new FileAttachment(supportRequestOpt.get(), file.getOriginalFilename(), filePath);
        fileAttachmentRepository.save(attachment);
    }

}
