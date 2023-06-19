package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ListConnectContacts extends BaseActivity {
  private Long advisorUserId;
  private String symphonyId;
  private String phoneNumber;
  private String emailAddress;
  private String network;
  private String before;
  private String after;
}
