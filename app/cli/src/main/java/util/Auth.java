package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Scanner;

import org.json.JSONObject;

import request.Request;
import request.RequestType;

public class Auth {
    private static String clientID = "a8c497924c3246920661";
    private static String githubDeviceCodeLink = "https://github.com/login/device/code";
    private static String githubLoginLink = "https://github.com/login/device";
    private static String checkAccessLink = "https://github.com/login/oauth/access_token";
    private static String githubUserLink = "https://api.github.com/user";

    public String runAuth(Scanner scanner) {
        JSONObject payload = new JSONObject();
        payload.put("client_id", clientID);

        Request request = new Request();
        HttpResponse<String> response = request.makeNonApiRequest(githubDeviceCodeLink, RequestType.POST,
                Optional.of(payload));
        JSONObject jsonResponse = convertGithubResponse(response.body());

        String userCode = jsonResponse.getString("user_code");
        System.out.println("Please visit " + githubLoginLink + " and enter " + userCode);

        System.out.println("Waiting for auth...");

        payload = new JSONObject();
        payload.put("client_id", clientID);
        payload.put("device_code", jsonResponse.getString("device_code"));
        payload.put("grant_type", "urn:ietf:params:oauth:grant-type:device_code");

        response = request.makeNonApiRequest(checkAccessLink, RequestType.POST,
                Optional.of(payload));
        jsonResponse = convertGithubResponse(response.body());

        while (!jsonResponse.has("access_token")) {
            try {
                Thread.sleep(5000);

                response = request.makeNonApiRequest(checkAccessLink, RequestType.POST,
                        Optional.of(payload));
                jsonResponse = convertGithubResponse(response.body());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return jsonResponse.getString("access_token");
    }

    public String getUsername(String token) {
        HttpRequest usernameRequest = HttpRequest.newBuilder().uri(URI.create(githubUserLink))
                .method(RequestType.GET.name(), HttpRequest.BodyPublishers.noBody())
                .header("Authorization", "Bearer " + token)
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(usernameRequest,
                    HttpResponse.BodyHandlers.ofString());
            JSONObject responseJson = new JSONObject(response.body());
            return responseJson.getString("login");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "";
    }

    private JSONObject convertGithubResponse(String responseString) {
        String[] responsePairs = responseString.split("&");
        JSONObject jsonResponse = new JSONObject();

        for (String pair : responsePairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length >= 2)
                jsonResponse.put(keyValue[0], keyValue[1]);
        }

        return jsonResponse;
    }
}
