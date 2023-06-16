package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.ContactApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DeleteConnectContactExecutor implements ActivityExecutor<DeleteConnectContact> {
  private final ContactApi contactApi;

  @Override
  public void execute(ActivityExecutorContext<DeleteConnectContact> context) {
    BigDecimal userId = new BigDecimal(context.getActivity().getUserId());
    context.setOutputVariable("contact", contactApi.deleteContactsBySymphonyIdAndExternalNetworkv2(userId));
  }
}
