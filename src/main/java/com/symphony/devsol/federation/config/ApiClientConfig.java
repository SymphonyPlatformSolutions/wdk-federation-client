package com.symphony.devsol.federation.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.symphony.bdk.core.auth.jwt.JwtHelper;
import com.symphony.devsol.federation.gen.*;
import com.symphony.devsol.federation.http.ApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class ApiClientConfig {
    @Value("${bdk.federation.publicKeyName:}")
    private String publicKeyName;
    @Value("${bdk.federation.privateKey.path:}")
    private String privateKeyPath;
    @Value("${bdk.federation.uri:}")
    private String connectUri;
    private RSAPrivateKey privateKey = null;
    private final RestTemplateBuilder restTemplateBuilder;

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

    @Bean
    public ApiClient federationApiClient() {
        DefaultUriBuilderFactory builderFactory = new DefaultUriBuilderFactory();
        builderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        RestTemplate restTemplate = restTemplateBuilder.additionalInterceptors((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken());
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }).uriTemplateHandler(builderFactory).build();

        return new ApiClient(restTemplate).setBasePath(connectUri);
    }

    @Bean
    public CustomerContactApi customerContactApi() {
        return new CustomerContactApi(federationApiClient());
    }

    @Bean
    public CustomerInviteApi customerInviteApi() {
        return new CustomerInviteApi(federationApiClient());
    }

    @Bean
    public EntitlementsApi entitlementsApi() {
        return new EntitlementsApi(federationApiClient());
    }

    @Bean
    public CustomerFederationGroupApi customerFederationGroupApi() {
        return new CustomerFederationGroupApi(federationApiClient());
    }

    @Bean
    public CustomerAccessControlApi customerAccessControlApi() {
        return new CustomerAccessControlApi(federationApiClient());
    }

    @Bean
    public CustomerRoomApi customerRoomApi() {
        return new CustomerRoomApi(federationApiClient());
    }

    @Bean
    public CustomerSearchApi customerSearchApi() {
        return new CustomerSearchApi(federationApiClient());
    }
}
