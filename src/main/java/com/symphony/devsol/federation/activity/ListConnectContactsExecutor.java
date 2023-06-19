package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.CustomerSearchApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListConnectContactsExecutor implements ActivityExecutor<ListConnectContacts> {
  private final CustomerSearchApi searchApi;

  @Override
  public void execute(ActivityExecutorContext<ListConnectContacts> context) {
    ListConnectContacts activity = context.getActivity();
    String network = activity.getNetwork();
    String advisorUserId = String.valueOf(activity.getAdvisorUserId());
    String before = activity.getBefore();
    String after = activity.getAfter();

    if (activity.getAdvisorUserId() != null) {
      context.setOutputVariable("contacts", searchApi.findContactsBySymId(network, advisorUserId, before, after));
    } else {
      context.setOutputVariable("contacts", searchApi.listContactsV2(
          network, activity.getEmailAddress(), activity.getPhoneNumber(), activity.getSymphonyId(), before, after));
    }
  }
}
