package com.symphony.devsol.federation.activity;

import com.symphony.bdk.core.util.IdUtil;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.CustomerRoomApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveConnectRoomMemberExecutor implements ActivityExecutor<RemoveConnectRoomMember> {
  private final CustomerRoomApi roomApi;

  @Override
  public void execute(ActivityExecutorContext<RemoveConnectRoomMember> context) {
    RemoveConnectRoomMember activity = context.getActivity();
    String memberSymphonyId = String.valueOf(activity.getMemberSymphonyId());
    String advisorSymphonyId = String.valueOf(activity.getAdvisorSymphonyId());
    String streamId = IdUtil.toUrlSafeIdIfNeeded(activity.getStreamId().trim());
    context.setOutputVariable("result", roomApi.removeRoomMemberV2(streamId, memberSymphonyId, advisorSymphonyId));
  }
}
