package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.client.FederationClient;
import com.symphony.devsol.federation.model.AddRoomMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddConnectRoomMemberExecutor implements ActivityExecutor<AddConnectRoomMember> {
  private final FederationClient client;

  @Override
  public void execute(ActivityExecutorContext<AddConnectRoomMember> context) {
    AddConnectRoomMember activity = context.getActivity();
    AddRoomMemberRequest request = AddRoomMemberRequest.builder()
        .advisorSymphonyId(activity.getAdvisorSymphonyId())
        .memberSymphonyId(activity.getMemberSymphonyId())
        .externalNetwork(activity.getExternalNetwork())
        .contact(activity.isContact())
        .build();
    client.addRoomMember(activity.getStreamId(), request);
  }
}
