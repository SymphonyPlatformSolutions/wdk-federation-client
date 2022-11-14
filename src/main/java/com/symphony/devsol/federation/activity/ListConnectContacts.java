package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.devsol.federation.model.ExternalNetwork;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ListConnectContacts extends BaseActivity {
  private long advisorUserId;
  private String phoneNumber;
  private String emailAddress;
  private ExternalNetwork network;
}
