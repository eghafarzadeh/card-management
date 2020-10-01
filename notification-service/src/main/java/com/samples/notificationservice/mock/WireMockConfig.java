package com.samples.notificationservice.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * @author Elham
 * @since 9/30/2020
 */
@Component
@Profile("dev")
public class WireMockConfig {
    @PostConstruct
    private void init() {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8087).extensions(new ResponseTemplateTransformer(true))); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.start();
        wireMockServer.stubFor(post(urlEqualTo("/messages/send-sms"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"result\": \"success\"")));
    }
}
