package com.symphony.devsol.federation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRoomResponse {
    private String streamId;
    private String roomName;
    private String advisorSymphonyId;
    private String created;
    private String updated;
    private String roomType;
    private int membersCount;
}
