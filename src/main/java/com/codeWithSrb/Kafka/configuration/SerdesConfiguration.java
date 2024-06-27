package com.codeWithSrb.Kafka.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerdesConfiguration {

    @Bean
    public Serdes serdes(@Value("${spring.kafka.producer.properties.schema.registry.url}") String schemaRegistryUrl) {
        return new Serdes(schemaRegistryUrl);
    }
}
