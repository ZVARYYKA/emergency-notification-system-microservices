package dev.zvaryyka.recipientservice.repository;

import dev.zvaryyka.recipientservice.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipientRepository  extends JpaRepository<Recipient, Long> {

    List<Recipient> findByKeycloakUserId(String keycloakUserId);

    Optional<Recipient> findById(UUID id);
}
