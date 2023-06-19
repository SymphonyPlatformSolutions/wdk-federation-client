package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.CustomerContactApi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteConnectContactExecutor implements ActivityExecutor<DeleteConnectContact> {
  private final CustomerContactApi contactApi;

  @Override
  public void execute(ActivityExecutorContext<DeleteConnectContact> context) {
    String userId = String.valueOf(context.getActivity().getUserId());
    context.setOutputVariable("contact", contactApi.deleteAdvisorContactsBySymphonyId(userId));
  }
}
