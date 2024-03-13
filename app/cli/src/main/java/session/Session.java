package session;

import com.models.Account;
import com.models.BeanType;
import com.models.User;

import request.UserRequest;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Session {
    private User currentUser;

    private List<BeanType> beanTypes;

    private List<Account> userAccounts;

    private static final String LINE_PROMPT = "> ";
    private static final String USERNAME_PROMPT = "Enter your username: ";
    private static final String WELCOME_USER_PROMPT_START = "Welcome, ";
    private static final String WELCOME_USER_PROMPT_END = "!";
    private static final String USER_NOT_FOUND_MESSAGE = "Username not found.";
    private static final String LOGOUT_COMMAND = "logout";
    private static final String ERROR_COMMAND = "error";

    public Session() {

    }

    public String startSession(Scanner scanner) {
        System.out.print(USERNAME_PROMPT);
        String username = scanner.nextLine();

        Optional<User> userOptional = UserRequest.getInstance().getUser(username);

        if (!userOptional.isPresent()) {
            System.out.println(USER_NOT_FOUND_MESSAGE);
            return ERROR_COMMAND;
        }

        System.out.println(WELCOME_USER_PROMPT_START + userOptional.get().getUsername() + WELCOME_USER_PROMPT_END);

        System.out.print(LINE_PROMPT);
        String userInput = scanner.nextLine();

        while (!userInput.equals(LOGOUT_COMMAND) && !userInput.equals(SessionManager.EXIT_COMMAND)) {
            System.out.print(LINE_PROMPT);
            userInput = scanner.nextLine();
        }

        if (userInput.equals(LOGOUT_COMMAND)) {
            return LOGOUT_COMMAND;
        }

        return SessionManager.EXIT_COMMAND;
    }
}
