package dev.zvaryyka.notificationgroupservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipientGroupMemberDTO {

    private UUID group_id;
    private UUID recipient_id;
}
