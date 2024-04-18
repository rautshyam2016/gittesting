package com.hbo.smart.serverapi.config;

import com.hbo.common.rest.client.config.RestClientProperties;
import com.hbo.smart.serverapi.service.APIClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that creates server bean of type	APIClientService for each controller with required
 * RestClientProperties.
 */
@Configuration
public class AppConfig {

    private static final String AFFILIATE_BASE_URL = "affiliate-subscriber";

    private static final String DEAL_BASE_URL = "deal";

    private static final String BILLING_BASE_URL = "billing";

    private static final String ADMIN_BASE_URL = "admin";

    private static final String BILLING_ENGINE_BASE_URL = "billing-engine";

    @Autowired
    private RestClientProperties restClientProperties;

    @Bean("affiliateSubscriberMSService")
    public APIClientService affiliateSubscriberService() {
       String baseUrl = this.restClientProperties.getApis().get(AFFILIATE_BASE_URL).getBaseUrl();
        return  new APIClientService(baseUrl);
    }

    @Bean("adminMSService")
    public APIClientService adminService() {
        String baseUrl = this.restClientProperties.getApis().get(ADMIN_BASE_URL).getBaseUrl();
        return  new APIClientService(baseUrl);
    }

    @Bean("dealMSService")
    public APIClientService dealService() {
        String baseUrl = this.restClientProperties.getApis().get(DEAL_BASE_URL).getBaseUrl();
        return  new APIClientService(baseUrl);
    }

    @Bean("billingMSService")
    public APIClientService billingService() {
        String baseUrl = this.restClientProperties.getApis().get(BILLING_BASE_URL).getBaseUrl();
        return  new APIClientService(baseUrl);
    }

    @Bean("billingEngineMSService")
    public APIClientService billingEngineService() {
        String baseUrl = this.restClientProperties.getApis().get(BILLING_ENGINE_BASE_URL).getBaseUrl();
        return  new APIClientService(baseUrl);
    }
}
