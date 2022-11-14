package com.symphony.devsol.federation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConnectContact {
    private String firstName;
    private String lastName;
    private String companyName;
    private String emailAddress;
    private String phoneNumber;
    private ExternalNetwork externalNetwork;
    private String onboarderEmailAddress;
}
