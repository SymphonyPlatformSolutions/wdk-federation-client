package com.symphony.devsol.federation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkConnectContactResponse {
    private List<BulkConnectContactEntry> members;
}
