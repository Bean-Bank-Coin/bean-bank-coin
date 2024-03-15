package request;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.json.JSONObject;

public class Request {
    private String urlBase = "http://";

    private final static String host = "54.195.208.130:8080/api/";

    private String token;

    public Request(String token) {
        urlBase = urlBase + host;
        this.token = token;
    }

    public Request() {
        urlBase = urlBase + host;
        this.token = "";
    }

    public HttpResponse<String> makeRequest(String endpointUrl, RequestType requestType, Optional<JSONObject> payload) {
        HttpRequest request;

        if (!payload.isPresent()) {
            request = HttpRequest.newBuilder().uri(URI.create(urlBase + endpointUrl))
                    .method(requestType.name(), HttpRequest.BodyPublishers.noBody())
                    .header("Authorization", "Bearer " + token)
                    .build();
        } else {
            request = HttpRequest.newBuilder().uri(URI.create(urlBase + endpointUrl))
                    .method(requestType.name(), HttpRequest.BodyPublishers.ofString(payload.get().toString()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .build();
        }

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HttpResponse<String> makeNonApiRequest(String url, RequestType requestType, Optional<JSONObject> payload) {
        HttpRequest request;

        if (!payload.isPresent()) {
            request = HttpRequest.newBuilder().uri(URI.create(url))
                    .method(requestType.name(), HttpRequest.BodyPublishers.noBody())
                    .header("Authorization", "Bearer " + token)
                    .build();
        } else {
            request = HttpRequest.newBuilder().uri(URI.create(url))
                    .method(requestType.name(), HttpRequest.BodyPublishers.ofString(payload.get().toString()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .build();
        }

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}