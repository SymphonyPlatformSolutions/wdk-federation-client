package com.symphony.devsol.federation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RenameRoomResponse {
    private String streamId;
    private String oldRoomName;
    private String newRoomName;
}
