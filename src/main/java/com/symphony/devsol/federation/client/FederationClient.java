package com.symphony.devsol.federation.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.symphony.bdk.core.auth.jwt.JwtHelper;
import com.symphony.devsol.federation.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FederationClient {
    @Value("${bdk.federation.publicKeyName:}")
    private String publicKeyName;
    @Value("${bdk.federation.privateKey.path:}")
    private String privateKeyPath;
    @Value("${bdk.federation.uri:}")
    private String connectUri;
    private RSAPrivateKey privateKey = null;
    private final RestTemplate restTemplate = new RestTemplate();
    private Instant tokenExpiry = Instant.now();
    private HttpHeaders headers;

    @PostConstruct
    public void init() throws Exception {
        if (!publicKeyName.isEmpty()) {
            String keyContent = Files.readString(Path.of(privateKeyPath));
            privateKey = (RSAPrivateKey) JwtHelper.parseRsaPrivateKey(keyContent);
        }
    }

    private String generateToken() {
        if (privateKey == null) {
            throw new RuntimeException("Federation configuration is missing");
        }
        Instant now = Instant.now();
        return JWT.create()
            .withSubject("ces:customer:" + publicKeyName)
            .withJWTId(UUID.randomUUID().toString())
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(now.plus(30, ChronoUnit.MINUTES)))
            .sign(Algorithm.RSA512(null, privateKey));
    }

    private HttpHeaders getHeaders() {
        if (Instant.now().isAfter(tokenExpiry)) {
            tokenExpiry = Instant.now().plus(30, ChronoUnit.MINUTES);
            headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.set("Authorization", "Bearer " + generateToken());
        }
        return headers;
    }

    public String addEntitlement(String advisorEmailAddress, ExternalNetwork externalNetwork) {
        Map<String, String> data = Map.of("advisorEmailAddress", advisorEmailAddress, "externalNetwork", externalNetwork.toString());
        return post("/api/v2/customer/entitlements", data, String.class);
    }

    public String addEntitlement(long symphonyId, ExternalNetwork externalNetwork) {
        Map<String, String> data = Map.of("symphonyId", String.valueOf(symphonyId), "externalNetwork", externalNetwork.toString());
        return post("/api/v2/customer/entitlements", data, String.class);
    }

    public String removeEntitlement(String advisorEmailAddress, ExternalNetwork externalNetwork) {
        String uri = String.format("/api/v2/customer/advisor/entitlements?advisorEmailAddress=%s&externalNetwork=%s", advisorEmailAddress, externalNetwork);
        return delete(uri, String.class);
    }

    public String getPermissions() {
        return get("/api/v1/customer/permissions", String.class);
    }

    public String getPermissions(ExternalNetwork externalNetwork, String advisorEmail) {
        String uri = String.format("/api/v1/customer/advisors/advisorEmailAddress/%s/externalNetwork/%s/permissions", advisorEmail, externalNetwork);
        return get(uri, String.class);
    }

    public String addPermissions(ExternalNetwork externalNetwork, String advisorEmail, String permissionName) {
        String uri = String.format("/api/v1/customer/advisors/advisorEmailAddress/%s/externalNetwork/%s/permissions", advisorEmail, externalNetwork);
        return post(uri, Map.of("permissionName", permissionName), String.class);
    }

    public String getEntitlements() {
        return get("/api/v1/customer/entitlements", String.class);
    }

    public ConnectContactSearch listContacts(ExternalNetwork externalNetwork, String next) {
        next = next == null ? "" : next.replaceFirst("\\?", "&");
        String uri = String.format("/api/v2/customer/contacts?externalNetwork=%s%s", externalNetwork, next);
        return get(uri, ConnectContactSearch.class);
    }

    public ConnectContactSearch listContactsForAdvisor(ExternalNetwork externalNetwork, long advisorUserId) {
        String uri = String.format("/api/v1/customer/advisors/advisorSymphonyId/%s/externalNetwork/%s/contacts", advisorUserId, externalNetwork);
        return get(uri, ConnectContactSearch.class);
    }

    public ConnectContactSearch findContactByPhoneNumber(ExternalNetwork externalNetwork, String phoneNumber) {
        String encodedPhoneNumber = URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8);
        String uri = String.format("/api/v2/customer/contacts?externalNetwork=%s&phoneNumber=%s", externalNetwork, encodedPhoneNumber);
        return get(uri, ConnectContactSearch.class);
    }

    public ConnectContactSearch findContactByEmailAddress(ExternalNetwork externalNetwork, String emailAddress) {
        String uri = String.format("/api/v2/customer/contacts?externalNetwork=%s&emailAddress=%s", externalNetwork, emailAddress);
        return get(uri, ConnectContactSearch.class);
    }

    public ConnectContactResponse addContact(ConnectContact connectContact) {
        return post("/api/v2/customer/contacts", connectContact, ConnectContactResponse.class);
    }

    public ContactReportResponse deleteContactFromAllAdvisors(long userId) {
        return delete("/api/v2/customer/contacts/" + userId, ContactReportResponse.class);
    }

    public CreateRoomResponse createRoom(CreateRoomRequest request) {
        return post("/api/v2/customer/rooms", request, CreateRoomResponse.class);
    }

    public RenameRoomResponse renameRoom(String streamId, String newRoomName) {
        Map<String, String> request = Map.of("newRoomName", newRoomName);
        return post(String.format("/api/v1/customer/rooms/%s/rename", streamId), request, RenameRoomResponse.class);
    }

    public ConnectContactResponse addRoomMember(String streamId, AddRoomMemberRequest request) {
        return post(String.format("/api/v2/customer/rooms/%s/members", streamId), request, ConnectContactResponse.class);
    }

    public BulkConnectContactResponse addRoomMember(BulkAddRoomMemberRequest request) {
        return post("/api/v2/customer/rooms/members", request, BulkConnectContactResponse.class);
    }

    public String deleteRoomMember(String streamId, String memberEmailAddress, ExternalNetwork externalNetwork, String advisorEmailAddress, boolean contact) {
        String url = String.format("/api/v1/customer/rooms/%s/members?memberEmailAddress=%s&externalNetwork=%s&advisorEmailAddress=%s&contact=%s",
            streamId, memberEmailAddress, externalNetwork, advisorEmailAddress, contact);
        return delete(url, String.class);
    }

    public ListRoomMembersResponse listRoomMembers(String streamId) {
        return get(String.format("/api/v1/customer/rooms/%s/members", streamId), ListRoomMembersResponse.class);
    }

    public ListRoomsResponse listRooms(ExternalNetwork externalNetwork, long advisorSymphonyId) {
        String uri = String.format("/api/v2/customer/rooms?owner=true&externalNetwork=%s&advisorSymphonyId=%s", externalNetwork, advisorSymphonyId);
        return get(uri, ListRoomsResponse.class);
    }

    @Retryable
    private <T> T get(String uri, Class<T> responseType) {
        HttpEntity<Object> request = new HttpEntity<>(getHeaders());
        return restTemplate.exchange(connectUri + uri, HttpMethod.GET, request, responseType).getBody();
    }

    @Retryable
    private <T> T post(String uri, Object object, Class<T> responseType) {
        HttpEntity<Object> request = new HttpEntity<>(object, getHeaders());
        return restTemplate.postForObject(connectUri + uri, request, responseType);
    }

    @Retryable
    private <T> T delete(String uri, Class<T> responseType) {
        HttpEntity<Object> request = new HttpEntity<>(getHeaders());
        return restTemplate.exchange(connectUri + uri, HttpMethod.DELETE, request, responseType).getBody();
    }
}
