package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.gen.CustomerRoomApi;
import com.symphony.devsol.federation.model.CustomerBulkRoomMemberItemResponse;
import com.symphony.devsol.federation.model.CustomerBulkRoomMemberMultiRoomRequestV2;
import com.symphony.devsol.federation.model.CustomerBulkRoomMemberResponse;
import com.symphony.devsol.federation.model.CustomerRoomMemberMultiRoomRequestV2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddConnectRoomMemberExecutor implements ActivityExecutor<AddConnectRoomMember> {
  private final CustomerRoomApi roomApi;

  @Override
  public void execute(ActivityExecutorContext<AddConnectRoomMember> context) {
    CustomerBulkRoomMemberResponse response = new CustomerBulkRoomMemberResponse();
    List<CustomerBulkRoomMemberItemResponse> members = new ArrayList<>();
    AddConnectRoomMember activity = context.getActivity();

    CustomerBulkRoomMemberMultiRoomRequestV2 request = new CustomerBulkRoomMemberMultiRoomRequestV2();
    request.setAdvisorSymphonyId(String.valueOf(activity.getAdvisorSymphonyId()));
    request.setExternalNetwork(activity.getExternalNetwork());

    List<CustomerRoomMemberMultiRoomRequestV2> requests = new ArrayList<>();
    for (String streamId : activity.getStreamIds()) {
      for (long userId : activity.getMemberSymphonyIds()) {
        CustomerRoomMemberMultiRoomRequestV2 req = new CustomerRoomMemberMultiRoomRequestV2();
        req.setMemberSymphonyId(String.valueOf(userId));
        req.setStreamId(streamId);
        requests.add(req);
      }
    }

    for (List<CustomerRoomMemberMultiRoomRequestV2> batch : split(requests)) {
      if (!members.isEmpty()) {
        try {
          Thread.sleep(300);
        } catch (InterruptedException ignore) {}
      }
      request.setRequests(batch);
      response = roomApi.addRoomMembersMultiRoomV2(request);
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
