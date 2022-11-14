package com.symphony.devsol.federation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListRoomMembersResponse {
    private List<ConnectContactResponse> members;
}
