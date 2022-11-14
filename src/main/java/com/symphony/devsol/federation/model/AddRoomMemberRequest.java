package com.symphony.devsol.federation.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRoomMemberRequest {
    private long memberSymphonyId;
    private long advisorSymphonyId;
    private ExternalNetwork externalNetwork;
    private boolean contact;
}
