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
                userObject.getString("email")
        ));
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
