package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.CustomerRoomApi;
import com.symphony.devsol.federation.model.CustomerRoomRequestV2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateConnectRoomExecutor implements ActivityExecutor<CreateConnectRoom> {
  private final CustomerRoomApi roomApi;

  @Override
  public void execute(ActivityExecutorContext<CreateConnectRoom> context) {
    CreateConnectRoom activity = context.getActivity();
    CustomerRoomRequestV2 request = new CustomerRoomRequestV2();
    request.setExternalNetwork(activity.getExternalNetwork());
    request.setRoomName(activity.getRoomName());
    request.setOwnerSymphonyId(String.valueOf(activity.getOwnerSymphonyId()));
    context.setOutputVariable("room", roomApi.createRoomv2(request));
  }
}
