package com.symphony.devsol.federation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectContactResponse {
    private String firstName;
    private String lastName;
    private String displayName;
    private String companyName;
    private String emailAddress;
    private String phoneNumber;
    private ExternalNetwork externalNetwork;
    private String symphonyId;
    private String streamId;
    private List<String> advisors;
}
