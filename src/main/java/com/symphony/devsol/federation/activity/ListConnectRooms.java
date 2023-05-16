package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=true)
public class ListConnectRooms extends BaseActivity {
  private BigDecimal advisorSymphonyId;
  private String externalNetwork;
  private String before;
  private String after;
}
