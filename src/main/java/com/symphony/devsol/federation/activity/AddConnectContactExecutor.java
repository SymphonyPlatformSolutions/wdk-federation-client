package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.ContactApi;
import com.symphony.devsol.federation.model.CustomerContactRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddConnectContactExecutor implements ActivityExecutor<AddConnectContact> {
  private final ContactApi contactApi;

  @Override
  public void execute(ActivityExecutorContext<AddConnectContact> context) {
    CustomerContactRequest contact = context.getActivity().getContact();
    context.setOutputVariable("contact", contactApi.addContact(contact));
  }
}
