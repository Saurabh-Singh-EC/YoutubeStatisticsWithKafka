package com.codeWithSrb.Kafka.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafka
@EnableKafkaStreams
@Import({ TopicsConfiguration.class, SerdesConfiguration.class })
public class KafkaStreamsConfig {
}
