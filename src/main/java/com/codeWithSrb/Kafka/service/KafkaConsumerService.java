package com.codeWithSrb.Kafka.service;

import jakarta.annotation.PostConstruct;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumerService {

    @PostConstruct
    public void performInIt() {
        System.out.println("Initializing kafka consumer");
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(List<ConsumerRecord<SpecificRecord, SpecificRecord>> records, Acknowledgment acknowledgment) {
        //logic to process the read records
        records.forEach(record -> System.out.println("consumer read the data from offset: " + record.offset()));
        acknowledgment.acknowledge();
    }
}