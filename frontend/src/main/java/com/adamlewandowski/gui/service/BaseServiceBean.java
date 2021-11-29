package com.adamlewandowski.gui.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@Transactional
@RequiredArgsConstructor
public abstract class BaseServiceBean {
    private final String username = "user";
    private final String password = "user123";
    protected final String BASE_URL = "http://localhost:8888/";

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
