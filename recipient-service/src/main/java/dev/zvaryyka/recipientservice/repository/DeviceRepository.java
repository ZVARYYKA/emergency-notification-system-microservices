package dev.zvaryyka.recipientservice.repository;

import dev.zvaryyka.recipientservice.models.Device;
import dev.zvaryyka.recipientservice.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

    List<Device> findAllByRecipient(Recipient recipient);

}
