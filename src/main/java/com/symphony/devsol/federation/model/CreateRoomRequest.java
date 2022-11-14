package com.symphony.devsol.federation.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRoomRequest {
    private String roomName;
    private long ownerSymphonyId;
    private ExternalNetwork externalNetwork;
}
