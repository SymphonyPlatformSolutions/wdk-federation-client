package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.devsol.federation.model.ExternalNetwork;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AddConnectRoomMember extends BaseActivity {
    private String streamId;
    private long memberSymphonyId;
    private long advisorSymphonyId;
    private ExternalNetwork externalNetwork;
    private boolean contact;
    private List<Long> memberSymphonyIds;
}
