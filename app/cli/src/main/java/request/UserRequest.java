package request;

import com.models.User;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.Optional;

public class UserRequest {
    private static UserRequest userRequest = null;

    public Optional<User> getUser(String username) {
        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("user/" + username, RequestType.GET, null);

        if (response.body().equals(""))
            return Optional.empty();

        JSONObject userObject = new JSONObject(response.body());

        return Optional.of(new User(
                userObject.getInt("userID"),
                userObject.getString("username"),
                userObject.getString("password"),
                userObject.getString("email")));
    }

    public User createUser(String username, String password, String email) {
        JSONObject payload = new JSONObject();
        payload.put("username", username);
        payload.put("password", password);
        payload.put("email", email);

        System.out.println(payload.toString());
        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("user", RequestType.POST, Optional.of(payload));

        if (response.body().equals(""))
            return null;

        System.out.println(response.body());
        JSONObject userObject = new JSONObject(response.body());

        return new User(
                userObject.getInt("userID"),
                userObject.getString("username"),
                userObject.getString("password"),
                userObject.getString("email"));
    }

    private UserRequest() {
    }

    public static UserRequest getInstance() {
        if (userRequest == null) {
            userRequest = new UserRequest();
        }

        return userRequest;
    }
}
