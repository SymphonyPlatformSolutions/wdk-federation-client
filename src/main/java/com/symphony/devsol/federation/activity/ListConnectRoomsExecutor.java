package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.client.FederationClient;
import com.symphony.devsol.federation.model.ListRoomsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListConnectRoomsExecutor implements ActivityExecutor<ListConnectRooms> {
  private final FederationClient client;

  @Override
  public void execute(ActivityExecutorContext<ListConnectRooms> context) {
    ListConnectRooms activity = context.getActivity();
    ListRoomsResponse rooms = client.listRooms(activity.getExternalNetwork(), activity.getAdvisorSymphonyId());
    context.setOutputVariable("rooms", rooms);
  }
}
