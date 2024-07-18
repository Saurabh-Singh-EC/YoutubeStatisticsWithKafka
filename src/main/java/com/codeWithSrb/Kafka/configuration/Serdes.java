package com.codeWithSrb.Kafka.configuration;


import com.codeWithSrb.Kafka.schema.VideoStatistics;
import com.codeWithSrb.Kafka.schema.VideoStatisticsKey;
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;

import java.util.HashMap;
import java.util.Map;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.KEY_SUBJECT_NAME_STRATEGY;
import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY;
import static io.confluent.kafka.serializers.KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG;

public class Serdes {

    private final Serde<VideoStatisticsKey> keySerde;
    private final Serde<VideoStatistics> valueSerde;

    public Serdes(String schemaRegistryUrl) {
        Map<String, Object> configs = new HashMap<>();
        configs.put("schema.registry.url", schemaRegistryUrl);
        configs.put(VALUE_SUBJECT_NAME_STRATEGY, TopicRecordNameStrategy.class.getName());
        configs.put(KEY_SUBJECT_NAME_STRATEGY, TopicRecordNameStrategy.class.getName());
        configs.put(SPECIFIC_AVRO_READER_CONFIG, true);

        keySerde = new SpecificAvroSerde<>();
        keySerde.configure(configs, true);

        valueSerde = new SpecificAvroSerde<>();
        valueSerde.configure(configs, false);
    }

    public Serde<VideoStatisticsKey> getKeySerde() {
        return keySerde;
    }

    public Serde<VideoStatistics> getValueSerde() {
        return valueSerde;
    }
}
