package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.RoomApi;
import com.symphony.devsol.federation.model.RoomRequestv2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateConnectRoomExecutor implements ActivityExecutor<CreateConnectRoom> {
  private final RoomApi roomApi;

  @Override
  public void execute(ActivityExecutorContext<CreateConnectRoom> context) {
    CreateConnectRoom activity = context.getActivity();
    RoomRequestv2 request = new RoomRequestv2();
    request.setExternalNetwork(activity.getExternalNetwork());
    request.setRoomName(activity.getRoomName());
    request.setOwnerSymphonyId(new BigDecimal(activity.getOwnerSymphonyId()));
    context.setOutputVariable("room", roomApi.createRoomv2(request));
  }
}
