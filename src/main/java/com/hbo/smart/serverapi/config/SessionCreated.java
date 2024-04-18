package com.hbo.smart.serverapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionCreationEvent;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class SessionCreated implements ApplicationListener<SessionCreationEvent> {

    @Override
    public void onApplicationEvent(SessionCreationEvent sessionCreationEvent) {
        HttpSessionCreatedEvent httpSessionCreatedEvent = (HttpSessionCreatedEvent)sessionCreationEvent;
        log.info("session created for {} at {}  and session id is {} ", "" , new Date(httpSessionCreatedEvent.getSession().getCreationTime()), httpSessionCreatedEvent.getSession().getId());

    }

}
