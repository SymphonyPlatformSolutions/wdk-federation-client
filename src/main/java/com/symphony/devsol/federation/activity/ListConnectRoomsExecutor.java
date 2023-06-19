package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.CustomerRoomApi;
import com.symphony.devsol.federation.model.CustomerRoomResponse;
import com.symphony.devsol.federation.model.CustomerRoomsResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListConnectRoomsExecutor implements ActivityExecutor<ListConnectRooms> {
  private final CustomerRoomApi roomApi;

  @Override
  public void execute(ActivityExecutorContext<ListConnectRooms> context) {
    ListConnectRooms activity = context.getActivity();
    List<CustomerRoomResponse> rooms = new ArrayList<>();
    String advisorId = String.valueOf(activity.getAdvisorSymphonyId());
    CustomerRoomsResponse response;
    if (activity.getBefore() != null || activity.getAfter() != null) {
      response = roomApi.listRoomsv2(advisorId, activity.getExternalNetwork(), true, activity.getBefore(), activity.getAfter());
    } else {
      String after = null;
      do {
        if (!rooms.isEmpty()) {
          try {
            Thread.sleep(300);
          } catch (InterruptedException ignore) {}
        }
        response = roomApi.listRoomsv2(advisorId, activity.getExternalNetwork(), true, null, after);
        rooms.addAll(response.getRooms());
        after = response.getPagination().getCursors().getAfter();
      } while (after != null);
      response = new CustomerRoomsResponse();
      response.setRooms(rooms);
    }
    context.setOutputVariable("rooms", response);
  }
}
