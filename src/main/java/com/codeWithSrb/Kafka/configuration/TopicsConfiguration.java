package com.codeWithSrb.Kafka.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ Topics.class})
public class TopicsConfiguration {
}
