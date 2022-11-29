package com.symphony.devsol.federation.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BulkAddRoomMemberEntry {
    private String streamId;
    private long memberSymphonyId;
}
