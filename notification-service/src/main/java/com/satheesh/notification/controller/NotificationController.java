package com.satheesh.notification.controller;

import com.satheesh.notification.kafka.event.ContactSubmittedEvent;
import com.satheesh.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody ContactSubmittedEvent event) {
        emailService.sendContactNotificationEmail(event);
        return ResponseEntity.ok("Email dispatched successfully");
    }
}
