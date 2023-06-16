package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ListConnectRooms extends BaseActivity {
  private long advisorSymphonyId;
  private String externalNetwork;
  private String before;
  private String after;
}
