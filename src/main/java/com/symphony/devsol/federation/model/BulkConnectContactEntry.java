package com.symphony.devsol.federation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkConnectContactEntry {
    private int status;
    private ConnectContactResponse response;
}
