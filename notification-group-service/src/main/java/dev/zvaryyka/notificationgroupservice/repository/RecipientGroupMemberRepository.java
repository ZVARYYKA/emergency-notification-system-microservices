package dev.zvaryyka.notificationgroupservice.repository;


import dev.zvaryyka.notificationgroupservice.model.RecipientGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipientGroupMemberRepository
        extends JpaRepository<RecipientGroupMember, UUID> {

    List<RecipientGroupMember> findByGroupId(UUID groupId);

}
