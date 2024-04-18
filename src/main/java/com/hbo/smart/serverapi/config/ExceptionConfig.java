package com.hbo.smart.serverapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbo.smart.serverapi.exception.SmartRestExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Configuration
public class ExceptionConfig {

    /**
     *  Overriding oauthRestTemplateErrorHandler bean from HBO starter to pass corrective actions.
     *  Details from errormessage not parsing JSON
     * @param objectMapper
     * @return
     */
    @Bean
    public DefaultResponseErrorHandler oauthRestTemplateErrorHandler(final ObjectMapper objectMapper) {
        return new SmartRestExceptionHandler(objectMapper);
    }

}
