package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.client.FederationClient;
import com.symphony.devsol.federation.model.ExternalNetwork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddConnectEntitlementExecutor implements ActivityExecutor<AddConnectEntitlement> {
  private final FederationClient client;

  @Override
  public void execute(ActivityExecutorContext<AddConnectEntitlement> context) {
    ExternalNetwork externalNetwork = context.getActivity().getExternalNetwork();
    long symphonyId = context.getActivity().getSymphonyId();
    String advisorEmailAddress = context.getActivity().getAdvisorEmailAddress();
    if (symphonyId > 0L) {
      context.setOutputVariable("entitlement", client.addEntitlement(symphonyId, externalNetwork));
    } else {
      context.setOutputVariable("entitlement", client.addEntitlement(advisorEmailAddress, externalNetwork));
    }
  }
}
