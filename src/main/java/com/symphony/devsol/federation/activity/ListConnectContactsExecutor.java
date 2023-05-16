package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.SearchApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListConnectContactsExecutor implements ActivityExecutor<ListConnectContacts> {
  private final SearchApi searchApi;

  @Override
  public void execute(ActivityExecutorContext<ListConnectContacts> context) {
    ListConnectContacts activity = context.getActivity();
    String network = activity.getNetwork();
    BigDecimal advisorUserId = activity.getAdvisorUserId();
    String before = activity.getBefore();
    String after = activity.getAfter();

    if (advisorUserId != null) {
      context.setOutputVariable("contacts", searchApi.findContactsOfAdvisorv2(network, advisorUserId, before, after));
    } else {
      context.setOutputVariable("contacts", searchApi.listContacts2(
          network, activity.getEmailAddress(), activity.getPhoneNumber(), activity.getSymphonyId(), before, after));
    }
  }
}
