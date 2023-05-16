package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.EntitlementsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddConnectEntitlementExecutor implements ActivityExecutor<AddConnectEntitlement> {
  private final EntitlementsApi entitlementsApi;

  @Override
  public void execute(ActivityExecutorContext<AddConnectEntitlement> context) {
    context.setOutputVariable("entitlement", entitlementsApi.addEntitlement(context.getActivity().getEntitlement()));
  }
}
