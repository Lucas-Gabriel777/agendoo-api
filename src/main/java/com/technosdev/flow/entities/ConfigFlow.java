package com.technosdev.flow.entities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigFlow {
    @Value("${WEBHOOK_VERIFY_TOKEN}")
    private String WEBHOOK_VERIFY_TOKEN;

    @Value("${ACCESS_TOKEN}")
    private String ACCESS_TOKEN;

    public String getWEBHOOK_VERIFY_TOKEN() {
        return WEBHOOK_VERIFY_TOKEN;
    }

    public String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }
}
