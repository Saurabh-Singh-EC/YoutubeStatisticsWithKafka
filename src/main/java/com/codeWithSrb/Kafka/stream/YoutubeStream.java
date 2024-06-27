package com.codeWithSrb.Kafka.stream;

import com.codeWithSrb.Kafka.schema.VideoStatistics;
import com.codeWithSrb.Kafka.configuration.Serdes;
import com.codeWithSrb.Kafka.configuration.Topics;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeStream {

    @Bean
    public KStream<SpecificRecord, VideoStatistics> createStream(
            StreamsBuilder streamsBuilder,
            Topics topics,
            Serdes serdes) {

        KStream<SpecificRecord, VideoStatistics> stream = streamsBuilder.stream(topics.getInput(), Consumed.with(serdes.getKeySerde(), serdes.getValueSerde()));
        var branch = stream.peek((key, value) -> System.out.println("Start Processing of record: " + value.getVideoTitle()))
                .split(Named.as("branch-"))
                .branch((key, value) -> true, Branched.as("success"))
                .noDefaultBranch();

        branch.get("branch-success")
                .to(topics.getOutput(), Produced.with(serdes.getKeySerde(), serdes.getValueSerde()));

        return stream;
    }
}