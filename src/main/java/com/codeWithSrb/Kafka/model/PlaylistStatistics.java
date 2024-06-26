package com.codeWithSrb.Kafka.model;

public class PlaylistStatistics {

    private String channelTitle;
    private String videoTitle;
    private String videoId;

    public PlaylistStatistics() {
    }

    public PlaylistStatistics(String channelTitle, String videoTitle, String videoId) {
        this.channelTitle = channelTitle;
        this.videoTitle = videoTitle;
        this.videoId = videoId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
