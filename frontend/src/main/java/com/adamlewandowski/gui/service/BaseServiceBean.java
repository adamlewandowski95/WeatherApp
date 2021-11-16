package com.adamlewandowski.gui.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
@Transactional
@RequiredArgsConstructor
public abstract class BaseServiceBean {
    private final String username = "user";
    private final String password = "user123";

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", encode64());
        return headers;
    }

    private String encode64() {
        String originalInput = username + ":" + password;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        String header = "Basic " + encodedString;
        return header;
    }

}
