package com.symphony.devsol.federation.activity;

import com.symphony.bdk.core.util.IdUtil;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.RoomApi;
import com.symphony.devsol.federation.model.RenameRoomRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RenameConnectRoomExecutor implements ActivityExecutor<RenameConnectRoom> {
  private final RoomApi roomApi;

  @Override
  public void execute(ActivityExecutorContext<RenameConnectRoom> context) {
    String streamId = IdUtil.toUrlSafeIdIfNeeded(context.getActivity().getStreamId().trim());
    RenameRoomRequest request = new RenameRoomRequest();
    request.setNewRoomName(context.getActivity().getNewRoomName().trim());
    context.setOutputVariable("rooms", roomApi.renameRoom(streamId, request));
  }
}
