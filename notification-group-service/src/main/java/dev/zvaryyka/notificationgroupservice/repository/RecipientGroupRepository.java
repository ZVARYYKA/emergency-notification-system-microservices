package dev.zvaryyka.notificationgroupservice.repository;

import dev.zvaryyka.notificationgroupservice.model.RecipientGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipientGroupRepository
        extends JpaRepository<RecipientGroup, UUID> {

    Optional<RecipientGroup> findById(UUID id);
}
