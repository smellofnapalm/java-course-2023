package edu.hw6;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HackerNews {
    final static private int DELAY = 5;
    final static private String ERROR_MESSAGE = "Название новости не было найдено!";

    private static HttpResponse<String> getResponse(URI uri) throws IOException, InterruptedException {
        HttpResponse<String> response;
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(DELAY)).build();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    static public long[] hackerNewsTopStories() {
        try {
            URI uri = URI.create("https://hacker-news.firebaseio.com/v0/topstories.json");
            var response = getResponse(uri);
            int n = response.body().length();
            return Arrays.stream(response.body().substring(1, n - 1).split(",")).mapToLong(Long::parseLong).toArray();
        } catch (IOException | InterruptedException e) {
            return new long[] {};
        }
    }

    static public String news(long id) {
        try {
            URI uri = new URI("https://hacker-news.firebaseio.com/v0/item/%d.json".formatted(id));
            var response = getResponse(uri);

            Pattern pattern = Pattern.compile("\\\"title\\\":\\\"([\\w ]+)\\\"");
            Matcher matcher = pattern.matcher(response.body());
            if (!matcher.find()) {
                throw new RuntimeException(ERROR_MESSAGE);
            }
            return matcher.group(1);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Id новости был указан некорректно!");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(ERROR_MESSAGE);
        }
    }

    private HackerNews() {
    }
}
