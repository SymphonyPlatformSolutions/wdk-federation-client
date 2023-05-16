package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.RoomApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListConnectRoomsExecutor implements ActivityExecutor<ListConnectRooms> {
  private final RoomApi roomApi;

  @Override
  public void execute(ActivityExecutorContext<ListConnectRooms> context) {
    ListConnectRooms activity = context.getActivity();
    context.setOutputVariable("rooms", roomApi.listRoomsv2(activity.getAdvisorSymphonyId(), activity.getExternalNetwork(),true, activity.getBefore(), activity.getAfter()));
  }
}
