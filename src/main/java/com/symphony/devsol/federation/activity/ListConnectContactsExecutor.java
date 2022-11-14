package com.symphony.devsol.federation.activity;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.devsol.federation.client.FederationClient;
import com.symphony.devsol.federation.model.ConnectContactResponse;
import com.symphony.devsol.federation.model.ConnectContactSearch;
import com.symphony.devsol.federation.model.ExternalNetwork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListConnectContactsExecutor implements ActivityExecutor<ListConnectContacts> {
  private final FederationClient client;

  @Override
  public void execute(ActivityExecutorContext<ListConnectContacts> context) {
    ListConnectContacts activity = context.getActivity();
    ExternalNetwork network = activity.getNetwork();
    long advisorUserId = activity.getAdvisorUserId();
    List<ConnectContactResponse> contacts;

    if (advisorUserId > 0L) {
      contacts = client.listContactsForAdvisor(network, advisorUserId).getContacts();
    } else if (activity.getPhoneNumber() != null) {
      contacts = client.findContactByPhoneNumber(network, activity.getPhoneNumber()).getContacts();
    } else if (activity.getEmailAddress() != null) {
      contacts = client.findContactByEmailAddress(network, activity.getEmailAddress()).getContacts();
    } else {
      ConnectContactSearch search = client.listContacts(network, null);
      contacts = new ArrayList<>(search.getContacts());

      while (search.getPagination() != null && search.getPagination().getNext() != null) {
        search = client.listContacts(network, search.getPagination().getNext());
        contacts.addAll(search.getContacts());
      }
    }
    context.setOutputVariable("contacts", contacts);
  }
}
