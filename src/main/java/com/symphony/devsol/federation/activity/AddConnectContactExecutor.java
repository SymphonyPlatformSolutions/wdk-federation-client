package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.client.FederationClient;
import com.symphony.devsol.federation.model.ConnectContact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddConnectContactExecutor implements ActivityExecutor<AddConnectContact> {
  private final FederationClient client;

  @Override
  public void execute(ActivityExecutorContext<AddConnectContact> context) {
    ConnectContact contact = context.getActivity().getContact();
    context.setOutputVariable("contact", client.addContact(contact));
  }
}
