package com.codeWithSrb.Kafka.controller;


import com.codeWithSrb.Kafka.enumeration.StatisticType;
import com.codeWithSrb.Kafka.model.PlaylistStatistics;
import com.codeWithSrb.Kafka.model.VideoStatistics;
import com.codeWithSrb.Kafka.service.SendToKafkaService;
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
    private final SendToKafkaService sendToKafkaService;

    public Controller(YoutubeService youtubeService, SendToKafkaService sendToKafkaService) {
        this.youtubeService = youtubeService;
        this.sendToKafkaService = sendToKafkaService;
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

        videoStatistics.forEach(videoStatistic -> {
            com.codeWirhSrb.Kafka.schema.VideoStatistics value = com.codeWirhSrb.Kafka.schema.VideoStatistics.newBuilder()
                    .setLikeCount(videoStatistic.getLikeCount())
                    .setCommentCount(videoStatistic.getCommentCount())
                    .setViewCount(videoStatistic.getViewCount())
                    .setFavoriteCount(videoStatistic.getFavoriteCount())
                    .setVideoTitle(videoStatistic.getVideoTitle())
                    .build();

            String key = videoStatistic.getVideoTitle() + videoStatistic.getLikeCount() + videoStatistic.getCommentCount() + videoStatistic.getFavoriteCount();
            sendToKafkaService.sendToKafka(key, value);
        });
    }
}