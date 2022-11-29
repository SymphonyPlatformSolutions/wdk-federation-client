package com.symphony.devsol.federation.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BulkAddRoomMemberRequest {
    private List<BulkAddRoomMemberEntry> requests;
    private long advisorSymphonyId;
    private ExternalNetwork externalNetwork;
}
