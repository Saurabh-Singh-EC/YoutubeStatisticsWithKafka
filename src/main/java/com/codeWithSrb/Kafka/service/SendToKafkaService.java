package com.codeWithSrb.Kafka.service;

import com.codeWithSrb.Kafka.configuration.Topics;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.CompletableFuture;

@Service
public class SendToKafkaService {

    private final Topics topics;

    private final KafkaTemplate<SpecificRecord, SpecificRecord> kafkaTemplate;

    public SendToKafkaService(Topics topics, KafkaTemplate<SpecificRecord, SpecificRecord> kafkaTemplate) {
        this.topics = topics;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToKafka(SpecificRecord key, SpecificRecord specificRecord) {
        CompletableFuture<SendResult<SpecificRecord, SpecificRecord>> futureResult = kafkaTemplate.send(topics.getInput(), key, specificRecord);
        futureResult.whenComplete((result, exception) -> {
            if (ObjectUtils.isEmpty(exception)) {
                System.out.println("message sent on offset: " + result.getRecordMetadata().offset());
            } else {
                System.out.println("error sending record to kafka topic: " + result.getRecordMetadata().topic() +
                        "failed with error: " + exception.getMessage());
            }
        });
    }
}
