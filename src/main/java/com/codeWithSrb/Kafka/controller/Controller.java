package com.codeWithSrb.Kafka.controller;


import com.codeWithSrb.Kafka.enumeration.StatisticType;
import com.codeWithSrb.Kafka.model.PlaylistStatistics;
import com.codeWithSrb.Kafka.model.VideoStatistics;
import com.codeWithSrb.Kafka.schema.VideoStatisticsKey;
import com.codeWithSrb.Kafka.service.KafkaProducerService;
import com.codeWithSrb.Kafka.service.YoutubeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/codeWithSrb/kafka")
@RestController
public class Controller {

    private final YoutubeService youtubeService;
    private final KafkaProducerService kafkaProducerService;

    public Controller(YoutubeService youtubeService, KafkaProducerService kafkaProducerService) {
        this.youtubeService = youtubeService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/youtube")
    public void getYoutubeStatistic() {
        List<PlaylistStatistics> youTubePlaylistStatistics = youtubeService.getYouTubePlaylistStatistics(StatisticType.PLAY_LIST_ID);
        List<VideoStatistics> videoStatistics = new ArrayList<>();

        youTubePlaylistStatistics.forEach(video -> {
            List<VideoStatistics> statistics = youtubeService.getStatisticsByVideoId(StatisticType.VIDEO_ID, video.getVideoId());
            statistics.forEach(statistic -> statistic.setVideoTitle(video.getVideoTitle()));
            videoStatistics.addAll(statistics);
        });

        sendToKafka(videoStatistics);
    }

    private void sendToKafka(List<VideoStatistics> videoStatistics) {

        videoStatistics.forEach(videoStatistic -> {
            com.codeWithSrb.Kafka.schema.VideoStatistics value = com.codeWithSrb.Kafka.schema.VideoStatistics.newBuilder()
                    .setLikeCount(videoStatistic.getLikeCount())
                    .setCommentCount(videoStatistic.getCommentCount())
                    .setViewCount(videoStatistic.getViewCount())
                    .setFavoriteCount(videoStatistic.getFavoriteCount())
                    .setVideoTitle(videoStatistic.getVideoTitle())
                    .build();

            String rawKey = videoStatistic.getVideoTitle() + videoStatistic.getLikeCount() + videoStatistic.getCommentCount() + videoStatistic.getFavoriteCount();

            VideoStatisticsKey key = VideoStatisticsKey.newBuilder()
                    .setVideoStatisticsId(rawKey)
                    .build();
            kafkaProducerService.sendToKafka(key, value);
        });
    }

}