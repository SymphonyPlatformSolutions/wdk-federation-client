package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class AddConnectRoomMember extends BaseActivity {
    private String externalNetwork;
    private BigDecimal advisorSymphonyId;
    private List<BigDecimal> memberSymphonyIds;
    private List<String> streamIds;
}
