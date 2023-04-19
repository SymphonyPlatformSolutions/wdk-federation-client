package com.symphony.devsol.federation.activity;

import com.symphony.bdk.core.util.IdUtil;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.client.FederationClient;
import com.symphony.devsol.federation.model.RenameRoomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RenameConnectRoomExecutor implements ActivityExecutor<RenameConnectRoom> {
  private final FederationClient client;

  @Override
  public void execute(ActivityExecutorContext<RenameConnectRoom> context) {
    String roomName = context.getActivity().getNewRoomName().trim();
    String streamId = IdUtil.toUrlSafeId(context.getActivity().getStreamId().trim());
    RenameRoomResponse renamedRoom = client.renameRoom(streamId, roomName);
    context.setOutputVariable("rooms", renamedRoom);
  }
}
