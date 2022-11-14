package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.client.FederationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteConnectContactExecutor implements ActivityExecutor<DeleteConnectContact> {
  private final FederationClient client;

  @Override
  public void execute(ActivityExecutorContext<DeleteConnectContact> context) {
    long userId = context.getActivity().getUserId();
    context.setOutputVariable("contact", client.deleteContactFromAllAdvisors(userId));
  }
}
