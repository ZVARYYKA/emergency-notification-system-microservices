package dev.zvaryyka.recipientservice.repository;

import dev.zvaryyka.recipientservice.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipientRepository  extends JpaRepository<Recipient, Long> {

    List<Recipient> findByKeycloakUserId(String keycloakUserId);

    Optional<Recipient> findById(UUID id);
}
