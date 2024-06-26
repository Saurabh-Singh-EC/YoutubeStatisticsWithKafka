package com.codeWithSrb.Kafka.common;

import com.codeWithSrb.Kafka.enumeration.StatisticType;
import org.springframework.stereotype.Service;

@Service
public class YoutubeStatisticStrategyHolder {

    private final VideoStatisticStrategy videoStatisticStrategy;
    private final PlayListStatisticStrategy playListStatisticStrategy;

    public YoutubeStatisticStrategyHolder() {
        this.videoStatisticStrategy = new VideoStatisticStrategy();
        this.playListStatisticStrategy = new PlayListStatisticStrategy();
    }

    public YoutubeStatisticStrategy getStatisticStrategy(StatisticType statisticType) {
        if(statisticType.name().equals(StatisticType.PLAY_LIST_ID.name())) {
            return playListStatisticStrategy;
        } else if (statisticType.name().equals(StatisticType.VIDEO_ID.name())) {
            return  videoStatisticStrategy;
        } else {
            throw new UnsupportedOperationException("Requested operation is not supported");
        }
    }
}
