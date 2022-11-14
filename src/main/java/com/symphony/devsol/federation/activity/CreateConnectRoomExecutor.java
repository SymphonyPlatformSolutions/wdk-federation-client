package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.client.FederationClient;
import com.symphony.devsol.federation.model.CreateRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateConnectRoomExecutor implements ActivityExecutor<CreateConnectRoom> {
  private final FederationClient client;

  @Override
  public void execute(ActivityExecutorContext<CreateConnectRoom> context) {
    CreateConnectRoom activity = context.getActivity();
    CreateRoomRequest request = CreateRoomRequest.builder()
        .roomName(activity.getRoomName())
        .ownerSymphonyId(activity.getOwnerSymphonyId())
        .externalNetwork(activity.getExternalNetwork())
        .build();
    context.setOutputVariable("room", client.createRoom(request));
  }
}
