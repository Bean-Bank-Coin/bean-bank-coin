package session;

import com.models.TransactionsType;
import com.models.User;
import request.Request;
import request.RequestType;
import request.UserRequest;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SessionManager {
    private static final String LINE_PROMPT = ">";
    private static final String USERNAME_PROMPT = "Enter your username: ";
    private static final String WELCOME_USER_PROMPT_START = "Welcome, ";
    private static final String WELCOME_USER_PROMPT_END = "!";

    private static final String USER_NOT_FOUND_MESSAGE = "Username not found.";

    private static final String LOGOUT_COMMAND = "logout";

    private List<TransactionsType> transactionsTypes;

    private Request request;

    public SessionManager() {
        request = new Request();
        HttpResponse response = request.makeRequest("transactionsType", RequestType.GET, null);
    }

    public void startSession() {
        Scanner scanner = new Scanner(System.in);

        System.out.print(USERNAME_PROMPT);
        String username = scanner.nextLine();

        Optional<User> userOptional = UserRequest.getInstance().getUser(username);

        if (!userOptional.isPresent()) {
            System.out.println(USER_NOT_FOUND_MESSAGE);
            return;
        }

        System.out.println(WELCOME_USER_PROMPT_START + userOptional.get().getUsername() + WELCOME_USER_PROMPT_END);

        System.out.println(LINE_PROMPT);
        String userInput = scanner.nextLine();

        while (!userInput.equals(LOGOUT_COMMAND)) {
            System.out.println(LINE_PROMPT);
            userInput = scanner.nextLine();
        }
    }
}
