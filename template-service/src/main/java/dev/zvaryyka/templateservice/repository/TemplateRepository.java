package dev.zvaryyka.templateservice.repository;

import dev.zvaryyka.templateservice.models.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TemplateRepository extends JpaRepository<MessageTemplate, UUID> {

    List<MessageTemplate> findAllByKeycloakUserId(UUID uuid);

}
