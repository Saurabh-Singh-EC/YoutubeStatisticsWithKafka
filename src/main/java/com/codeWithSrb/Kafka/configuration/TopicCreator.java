package com.codeWithSrb.Kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicCreator {

    private final String inputTopic;
    private final String outputTopic;
    private final String errorTopic;


    public TopicCreator(@Value("${kafka.topics.input}") String inputTopic, @Value("${kafka.topics.output}") String outputTopic, @Value("${kafka.topics.error}") String errorTopic) {
        this.inputTopic = inputTopic;
        this.outputTopic = outputTopic;
        this.errorTopic = errorTopic;
    }

    @Bean
    NewTopic inputTopic() {
        return new NewTopic(inputTopic, 1, (short) 1);
    }

    @Bean
    NewTopic outputTopic() {
        return new NewTopic(outputTopic, 1, (short) 1);
    }

    @Bean
    NewTopic errorTopic() {
        return new NewTopic(errorTopic, 1, (short) 1);
    }
}
