package com.codeWithSrb.Kafka.model;

import java.util.Objects;

public class VideoStatistics {

    private String viewCount;
    private String likeCount;
    private String favoriteCount;
    private String commentCount;
    private String videoTitle;

    public VideoStatistics() {
    }

    public VideoStatistics(String viewCount, String likeCount, String favoriteCount, String commentCount, String videoTitle) {
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.favoriteCount = favoriteCount;
        this.commentCount = commentCount;
        this.videoTitle = videoTitle;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoStatistics that)) return false;
        return Objects.equals(viewCount, that.viewCount) && Objects.equals(likeCount, that.likeCount) && Objects.equals(favoriteCount, that.favoriteCount) && Objects.equals(commentCount, that.commentCount) && Objects.equals(videoTitle, that.videoTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewCount, likeCount, favoriteCount, commentCount, videoTitle);
    }
}
