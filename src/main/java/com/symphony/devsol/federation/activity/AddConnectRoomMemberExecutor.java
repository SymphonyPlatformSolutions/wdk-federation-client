package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.RoomApi;
import com.symphony.devsol.federation.model.BulkRoomMemberItemResponsev2;
import com.symphony.devsol.federation.model.BulkRoomMemberMultiRoomRequestv2;
import com.symphony.devsol.federation.model.BulkRoomMemberResponsev2;
import com.symphony.devsol.federation.model.RoomMemberMultiRoomRequestv2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddConnectRoomMemberExecutor implements ActivityExecutor<AddConnectRoomMember> {
  private final RoomApi roomApi;

  @Override
  public void execute(ActivityExecutorContext<AddConnectRoomMember> context) {
    BulkRoomMemberResponsev2 response = new BulkRoomMemberResponsev2();
    List<BulkRoomMemberItemResponsev2> members = new ArrayList<>();
    AddConnectRoomMember activity = context.getActivity();

    BulkRoomMemberMultiRoomRequestv2 request = new BulkRoomMemberMultiRoomRequestv2();
    request.setAdvisorSymphonyId(activity.getAdvisorSymphonyId());
    request.setExternalNetwork(activity.getExternalNetwork());

    List<RoomMemberMultiRoomRequestv2> requests = new ArrayList<>();
    for (String streamId : activity.getStreamIds()) {
      for (BigDecimal userId : activity.getMemberSymphonyIds()) {
        RoomMemberMultiRoomRequestv2 req = new RoomMemberMultiRoomRequestv2();
        req.setMemberSymphonyId(userId);
        req.setStreamId(streamId);
        requests.add(req);
      }
    }

    for (List<RoomMemberMultiRoomRequestv2> batch : split(requests)) {
      if (!members.isEmpty()) {
        try {
          Thread.sleep(300);
        } catch (InterruptedException ignore) {}
      }
      request.setRequests(batch);
      response = roomApi.addRoomMembersMultiRoomv2(request);
      members.addAll(response.getMembers());
    }
    response.setMembers(members);
    context.setOutputVariable("result", response);
  }

  private <T> Collection<List<T>> split(List<T> inputList) {
    AtomicInteger counter = new AtomicInteger();
    return inputList.stream()
        .collect(Collectors.groupingBy(gr -> counter.getAndIncrement() / 20))
        .values();
  }
}
