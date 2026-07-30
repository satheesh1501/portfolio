package com.satheesh.portfolio.repository;

import com.satheesh.portfolio.PortfolioServiceApplication;
import com.satheesh.portfolio.entity.ContactMessage;
import com.satheesh.portfolio.enums.ContactStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ContextConfiguration(classes = PortfolioServiceApplication.class)
class ContactMessageRepositoryIntegrationTest {

    @Autowired
    private ContactMessageRepository repository;

    @Test
    @DisplayName("Should query duplicate contact messages by email submitted after given threshold time")
    void testFindByEmailAndCreatedAtAfter() {
        ContactMessage msg = ContactMessage.builder()
                .name("Jane Doe")
                .email("jane@example.com")
                .subject("Inquiry")
                .message("Hello Satheesh!")
                .ipAddress("127.0.0.1")
                .status(ContactStatus.PENDING)
                .build();

        repository.save(msg);

        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<ContactMessage> found = repository.findByEmailAndCreatedAtAfter("jane@example.com", tenMinutesAgo);

        assertFalse(found.isEmpty());
        assertEquals("Jane Doe", found.get(0).getName());
    }
}
