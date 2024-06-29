package com.codeWithSrb.Kafka.common;

import org.apache.kafka.common.errors.ApiException;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PlayListStatisticStrategy implements YoutubeStatisticStrategy {
    private static final String PART1_URL= "https://youtube.googleapis.com/youtube/v3/playlistItems?part=contentDetails&part=snippet&";
    private static final String PART2_URL= "playlistId=<PLAYLIST_ID>=";
    private static final String PART3_URL= "<API_KEY>";


    public String getStatistics(String nextPageToken){

        String youTubeApiUrl;
        if(ObjectUtils.isEmpty(nextPageToken)) {
            youTubeApiUrl = String.format("%s%s%s", PART1_URL, PART2_URL, PART3_URL);
        } else {
            String tokenPart = String.format("pageToken=%s&", nextPageToken);
            youTubeApiUrl = String.format("%s%s%s%s", PART1_URL, tokenPart, PART2_URL, PART3_URL);
        }

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(youTubeApiUrl))
                    .version(HttpClient.Version.HTTP_2)
                    .timeout(Duration.ofMinutes(1))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException exception) {
            throw new RuntimeException("Request url is not correct. Please provide the correct url");
        } catch (InterruptedException exception) {
            throw new RuntimeException("Send request failed to server. Please try again");
        } catch (Exception exception) {
            throw new ApiException("An exception occurred. Please try again");
        }
    }

    @Override
    public String getName() {
        return PlayListStatisticStrategy.class.getName();
    }
}
