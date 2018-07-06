package org.igrator.reactivetest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.MessageChannelReactiveUtils;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

@Configuration
public class IntegrationConfig {
    @Bean
    public SubscribableChannel messageChannel() {
        return MessageChannels.publishSubscribe()
//                .errorHandler(Throwable::printStackTrace)
                .get();
    }
}
