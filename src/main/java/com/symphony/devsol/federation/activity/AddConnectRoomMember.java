package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.swadl.v1.activity.OboActivity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class AddConnectRoomMember extends OboActivity {
    private String externalNetwork;
    private long advisorSymphonyId;
    private List<Long> memberSymphonyIds;
    private List<String> streamIds;
}
