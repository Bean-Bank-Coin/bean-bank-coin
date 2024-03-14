package request;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.models.User;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.Optional;

public class UserRequest {
    private static UserRequest userRequest = null;

    public Optional<User> getUser(String username, Optional<String> emailOptional) {
        JSONObject payload = new JSONObject();
        payload.put("username", username);

        if (emailOptional.isPresent()) {
            payload.put("email", emailOptional.get());
        }

        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("user", RequestType.GET, Optional.of(payload));

        if (response.body().isEmpty())
            return Optional.empty();

        JSONObject userObject = new JSONObject(response.body());

        return Optional.of(new User(
                userObject.getInt("userID"),
                userObject.getString("username"),
                userObject.getString("password"),
                userObject.getString("email")));
    }

    public Optional<User> createUser(String username, String password, String email) {
        JSONObject payload = new JSONObject();
        payload.put("username", username);
        payload.put("password", password);
        payload.put("email", email);

        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("user", RequestType.POST, Optional.of(payload));

        if (response.body().equals(""))
            return Optional.empty();

        JSONObject userObject = new JSONObject(response.body());

        return Optional.of(new User(
                userObject.getInt("userID"),
                userObject.getString("username"),
                userObject.getString("password"),
                userObject.getString("email")));
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
