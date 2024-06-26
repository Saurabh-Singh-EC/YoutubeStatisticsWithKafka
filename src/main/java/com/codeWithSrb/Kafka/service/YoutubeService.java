package com.codeWithSrb.Kafka.service;

import com.codeWithSrb.Kafka.common.YoutubeStatisticStrategy;
import com.codeWithSrb.Kafka.common.YoutubeStatisticStrategyHolder;
import com.codeWithSrb.Kafka.enumeration.StatisticType;
import com.codeWithSrb.Kafka.model.PlaylistStatistics;
import com.codeWithSrb.Kafka.model.VideoStatistics;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeService {

    private final YoutubeStatisticStrategyHolder youtubeStatisticStrategyHolder;
    private static final String NEXT_PAGE_TOKEN = "nextPageToken";

    public YoutubeService(YoutubeStatisticStrategyHolder youtubeStatisticStrategyHolder) {
        this.youtubeStatisticStrategyHolder = youtubeStatisticStrategyHolder;
    }

    public List<PlaylistStatistics> getYouTubePlaylistStatistics(StatisticType type) {
        String nextPageToken = null;
        String completeStatistics = getStatistics(type, nextPageToken);

        JsonObject completePlaylistStatisticsObject = (JsonObject)JsonParser.parseString(completeStatistics);
        nextPageToken = extractNextPageToken(completePlaylistStatisticsObject);

        List<PlaylistStatistics> videoRelevantStatistics = createVideoRelevantStatistics(completePlaylistStatisticsObject);

        while(!isNextPageTokenNull(nextPageToken)) {
            completeStatistics = getStatistics(type, nextPageToken);
            completePlaylistStatisticsObject = (JsonObject)JsonParser.parseString(completeStatistics);
            nextPageToken = extractNextPageToken(completePlaylistStatisticsObject);
            videoRelevantStatistics.addAll(createVideoRelevantStatistics(completePlaylistStatisticsObject));
        }

        return videoRelevantStatistics;
    }

    public List<VideoStatistics> getStatisticsByVideoId(StatisticType type, String videoId) {

        String videoStatistics = getStatistics(type, videoId);
        JsonObject videoStatisticsObject = (JsonObject)JsonParser.parseString(videoStatistics);
        JsonArray items = (JsonArray)videoStatisticsObject.get("items");

        List<VideoStatistics> statistics = new ArrayList<>();

        items.forEach(item -> {
            JsonObject itemObject = (JsonObject) item;
            JsonObject statisticsObject = (JsonObject)itemObject.get("statistics");
            String viewCount = statisticsObject.get("viewCount").getAsString();
            String likeCount = statisticsObject.get("likeCount").getAsString();
            String favoriteCount = statisticsObject.get("favoriteCount").getAsString();
            String commentCount = statisticsObject.get("commentCount").getAsString();
            statistics.add(new VideoStatistics(viewCount, likeCount, favoriteCount, commentCount, null));
        });
        return statistics;
    }

    public String getStatistics(StatisticType type, String nextPageToken) {
        YoutubeStatisticStrategy statisticStrategy = youtubeStatisticStrategyHolder.getStatisticStrategy(type);
        return statisticStrategy.getStatistics(nextPageToken);
    }

    private List<PlaylistStatistics> createVideoRelevantStatistics(JsonObject youtubeStatistics) {

        List<PlaylistStatistics> playlistStatistics = new ArrayList<>();
        JsonArray items = (JsonArray) youtubeStatistics.get("items");
        items.forEach(item -> {
            JsonObject itemObject = (JsonObject) item;
            JsonObject snippetObject = (JsonObject)itemObject.get("snippet");
            JsonObject contentDetailsObject = (JsonObject)itemObject.get("contentDetails");

            String channelTitle = snippetObject.get("channelTitle").getAsString();
            String videoTitle = snippetObject.get("title").getAsString();
            String videoId = contentDetailsObject.get("videoId").getAsString();

            playlistStatistics.add(new PlaylistStatistics(channelTitle, videoTitle, videoId));
        });
        return playlistStatistics;
    }

    private boolean isNextPageTokenNull(String nextPageToken) {
        return ObjectUtils.isEmpty(nextPageToken);
    }

    private String extractNextPageToken(JsonObject completePlaylistStatisticsObject) {
        if(ObjectUtils.isEmpty(completePlaylistStatisticsObject.get(NEXT_PAGE_TOKEN))) {
            return null;
        }
        return completePlaylistStatisticsObject.get(NEXT_PAGE_TOKEN).getAsString();
    }
}