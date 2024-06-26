package com.codeWithSrb.Kafka.common;

import com.codeWithSrb.Kafka.model.VideoStatistics;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class VideoStatisticStrategy implements YoutubeStatisticStrategy {


    private static final String PART1_URL= "https://youtube.googleapis.com/youtube/v3/videos?part=statistics&";
    private static final String PART2_URL= "id=";
    private static final String PART3_URL = "&key=<API_KEY>";


    public String getStatistics(String videoId){
        String videoIdUrlPart = PART2_URL + videoId;
        String youTubeApiUrl = String.format("%s%s%s", PART1_URL, videoIdUrlPart, PART3_URL);

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
        return VideoStatisticStrategy.class.getName();
    }
}
