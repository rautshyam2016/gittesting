package com.hbo.smart.serverapi.config;

import com.hbo.common.web.sso.core.SsoUser;
import com.hbo.smart.serverapi.service.APIClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@Slf4j
public class SessionTimeout implements ApplicationListener<SessionDestroyedEvent> {

    @Inject
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    @Qualifier("affiliateSubscriberMSService")
    private APIClientService affiliateSubscriberService;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent sessionDestroyedEvent) {


        HttpSessionDestroyedEvent httpSessionDestroyedEvent = (HttpSessionDestroyedEvent) sessionDestroyedEvent;
        Authentication authentication = sessionDestroyedEvent.getSecurityContexts().get(0).getAuthentication();
        String userId = ((SsoUser) authentication.getPrincipal()).getUsername().toUpperCase();

        log.info("session destroyed for {} and session id is {}", userId, httpSessionDestroyedEvent.getSession().getId());

        oAuth2RestTemplate.delete(affiliateSubscriberService.getBaseUrl() + "api/affiliate-subscriber/v1/unlocks/" + userId);
        log.info(" locks released for " + userId);
    }
}
