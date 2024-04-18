package com.hbo.smart.serverapi.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbo.common.rest.client.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

@Slf4j
public class SmartRestExceptionHandler extends DefaultResponseErrorHandler {

    private ObjectMapper mapper;

    public SmartRestExceptionHandler(ObjectMapper objectMapper) {
        super();
        this.mapper = objectMapper;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        try {
            super.handleError(response);
        } catch (HttpStatusCodeException ex) {
            try {
                String body = ex.getResponseBodyAsString();
                JsonNode node = mapper.readTree(body.replace("\\", "").replace("\"{", "{").replace("}\"", "}"));

                int status = 0;
                String message = null;
                String details = null;
                String developerMessage = null;
                Object rules = null;

                if (node.has("status")) {
                    status = node.get("status").intValue();
                }
                if (node.has("message")) {
                    message = node.get("message").textValue();
                }
                if (node.has("details")) {
                    details = node.get("details").textValue();
                }
                if (node.has("developerMessage")) {
                    developerMessage = node.get("developerMessage").textValue();
                }
                if (node.has("rules")) {
                    rules = node.get("rules");
                }
                if (status > 0 || message != null || details != null || developerMessage != null || rules != null) {
                    throw new SmartException(status, message, details, developerMessage, rules);
                } else {
                    throw ex;
                }
            } catch (RestException re) {
                throw re;
            } catch (Throwable t) {
                throw ex;
            }
        }
    }
}