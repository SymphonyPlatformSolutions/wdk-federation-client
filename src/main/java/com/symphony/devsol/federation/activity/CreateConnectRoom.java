package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class CreateConnectRoom extends BaseActivity {
  private String roomName;
  private long ownerSymphonyId;
  private String externalNetwork;
}
