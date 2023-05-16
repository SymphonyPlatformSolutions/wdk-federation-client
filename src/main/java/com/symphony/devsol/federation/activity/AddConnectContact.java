package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.devsol.federation.model.CustomerContactRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AddConnectContact extends BaseActivity {
  private CustomerContactRequest contact;
}
