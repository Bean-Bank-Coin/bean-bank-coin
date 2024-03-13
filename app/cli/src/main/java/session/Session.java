package session;

import com.models.Account;
import com.models.BeanType;
import com.models.User;

import request.UserRequest;
import util.UserInputHandler;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Session {
    private User currentUser;

    private List<BeanType> beanTypes;

    private List<Account> userAccounts;

    private static final String LINE_PROMPT = "> ";
    private static final String WELCOME_USER_PROMPT_START = "Welcome, ";
    private static final String WELCOME_USER_PROMPT_END = "!";
    private static final String USER_NOT_FOUND_MESSAGE = "Username not found.";
    private static final String LOGOUT_COMMAND = "logout";
    private static final String ERROR_COMMAND = "error";

    public Session() {

    }

    public User signInOrRegisterUser(Scanner scanner) {
        final String LOG_IN_SIGN_UP_PROMPT = "Login (L) or Register (R) ";
        final String USERNAME_PROMPT = "Enter your username: ";

        Console console = System.console();
        UserInputHandler inputHandler = UserInputHandler.getInstance();
        String userOption = inputHandler.handleUserInput(scanner, LOG_IN_SIGN_UP_PROMPT,
                Arrays.asList("L", "R"));

        if (userOption.equals("L")) {
            String username = inputHandler.handleUserInput(scanner, USERNAME_PROMPT, Collections.emptyList());
            String password = new String(console.readPassword("Enter your password: "));

            Optional<User> userOptional = UserRequest.getInstance().getUser(username);

            if (!userOptional.isPresent()) {
                System.out.println(USER_NOT_FOUND_MESSAGE);
                return null;
            }

            if (!password.equals(userOptional.get().getPassword())) {
                System.out.println("Incorrect password");
                return null;
            }

            return userOptional.get();
        }

        String username = inputHandler.handleUserInput(scanner, USERNAME_PROMPT, Collections.emptyList());
        String email = inputHandler.handleUserInput(scanner, "Enter your email: ", Collections.emptyList());
        String password = inputHandler.handleUserInput(scanner, "Enter your password: ", Collections.emptyList());

        Optional<User> userOptional = UserRequest.getInstance().getUser(username);

        if (userOptional.isPresent())
            System.out.println("Username already exists.");

        return UserRequest.getInstance().createUser(username, password, email);
    }

    public String startSession(Scanner scanner) {
        User currUser = signInOrRegisterUser(scanner);

        if (currUser == null) {
            return LOGOUT_COMMAND;
        }

        System.out.println(WELCOME_USER_PROMPT_START + currUser.getUsername() + WELCOME_USER_PROMPT_END);

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
