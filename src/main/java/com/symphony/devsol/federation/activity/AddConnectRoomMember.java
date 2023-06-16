package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class AddConnectRoomMember extends BaseActivity {
    private String externalNetwork;
    private long advisorSymphonyId;
    private List<Long> memberSymphonyIds;
    private List<String> streamIds;
}
