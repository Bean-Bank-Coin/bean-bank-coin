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
    private static final String WELCOME_TO_THE_CLI = " __    __    ___  _         __   ___   ___ ___    ___      ______   ___                   \r\n"
            + //
            "|  T__T  T  /  _]| T       /  ] /   \\ |   T   T  /  _]    |      T /   \\                  \r\n" + //
            "|  |  |  | /  [_ | |      /  / Y     Y| _   _ | /  [_     |      |Y     Y                 \r\n" + //
            "|  |  |  |Y    _]| l___  /  /  |  O  ||  \\_/  |Y    _]    l_j  l_j|  O  |                 \r\n" + //
            "l  `  '  !|   [_ |     T/   \\_ |     ||   |   ||   [_       |  |  |     |                 \r\n" + //
            " \\      / |     T|     |\\     |l     !|   |   ||     T      |  |  l     !                 \r\n" + //
            "  \\_/\\_/  l_____jl_____j \\____j \\___/ l___j___jl_____j      l__j   \\___/                  \r\n" + //
            "                                                                                          \r\n" + //
            " ____     ___   ____  ____       ____    ____  ____   __  _         __   ___  ____  ____  \r\n" + //
            "|    \\   /  _] /    T|    \\     |    \\  /    T|    \\ |  l/ ]       /  ] /   \\l    j|    \\ \r\n" + //
            "|  o  ) /  [_ Y  o  ||  _  Y    |  o  )Y  o  ||  _  Y|  ' /       /  / Y     Y|  T |  _  Y\r\n" + //
            "|     TY    _]|     ||  |  |    |     T|     ||  |  ||    \\      /  /  |  O  ||  | |  |  |\r\n" + //
            "|  O  ||   [_ |  _  ||  |  |    |  O  ||  _  ||  |  ||     Y    /   \\_ |     ||  | |  |  |\r\n" + //
            "|     ||     T|  |  ||  |  |    |     ||  |  ||  |  ||  .  |    \\     |l     !j  l |  |  |\r\n" + //
            "l_____jl_____jl__j__jl__j__j    l_____jl__j__jl__j__jl__j\\_j     \\____j \\___/|____jl__j__j\r\n" + //
            "                                                                                          \r\n" + //
            "                                                                                          \r\n" + //
            "                                                                                          ";

    private static final String GOODBYE_COMMAND = " ______  __ __   ____  ____   __  _      __ __   ___   __ __      _____   ___   ____      \r\n"
            + //
            "|      T|  T  T /    T|    \\ |  l/ ]    |  T  T /   \\ |  T  T    |     | /   \\ |    \\     \r\n" + //
            "|      ||  l  |Y  o  ||  _  Y|  ' /     |  |  |Y     Y|  |  |    |   __jY     Y|  D  )    \r\n" + //
            "l_j  l_j|  _  ||     ||  |  ||    \\     |  ~  ||  O  ||  |  |    |  l_  |  O  ||    /     \r\n" + //
            "  |  |  |  |  ||  _  ||  |  ||     Y    l___, ||     ||  :  |    |   _] |     ||    \\     \r\n" + //
            "  |  |  |  |  ||  |  ||  |  ||  .  |    |     !l     !l     |    |  T   l     !|  .  Y    \r\n" + //
            "  l__j  l__j__jl__j__jl__j__jl__j\\_j    l____/  \\___/  \\__,_j    l__j    \\___/ l__j\\_j    \r\n" + //
            "                                                                                          \r\n" + //
            " ____    ____  ____   __  _  ____  ____    ____      __    __  ____  ______  __ __        \r\n" + //
            "|    \\  /    T|    \\ |  l/ ]l    j|    \\  /    T    |  T__T  Tl    j|      T|  T  T       \r\n" + //
            "|  o  )Y  o  ||  _  Y|  ' /  |  T |  _  YY   __j    |  |  |  | |  T |      ||  l  |       \r\n" + //
            "|     T|     ||  |  ||    \\  |  | |  |  ||  T  |    |  |  |  | |  | l_j  l_j|  _  |       \r\n" + //
            "|  O  ||  _  ||  |  ||     Y |  | |  |  ||  l_ |    l  `  '  ! |  |   |  |  |  |  |       \r\n" + //
            "|     ||  |  ||  |  ||  .  | j  l |  |  ||     |     \\      /  j  l   |  |  |  |  |       \r\n" + //
            "l_____jl__j__jl__j__jl__j\\_j|____jl__j__jl___,_j      \\_/\\_/  |____j  l__j  l__j__j       \r\n" + //
            "                                                                                          \r\n" + //
            " ____     ___   ____  ____       ____    ____  ____   __  _         __   ___  ____  ____  \r\n" + //
            "|    \\   /  _] /    T|    \\     |    \\  /    T|    \\ |  l/ ]       /  ] /   \\l    j|    \\ \r\n" + //
            "|  o  ) /  [_ Y  o  ||  _  Y    |  o  )Y  o  ||  _  Y|  ' /       /  / Y     Y|  T |  _  Y\r\n" + //
            "|     TY    _]|     ||  |  |    |     T|     ||  |  ||    \\      /  /  |  O  ||  | |  |  |\r\n" + //
            "|  O  ||   [_ |  _  ||  |  |    |  O  ||  _  ||  |  ||     Y    /   \\_ |     ||  | |  |  |\r\n" + //
            "|     ||     T|  |  ||  |  |    |     ||  |  ||  |  ||  .  |    \\     |l     !j  l |  |  |\r\n" + //
            "l_____jl_____jl__j__jl__j__j    l_____jl__j__jl__j__jl__j\\_j     \\____j \\___/|____jl__j__j\r\n" + //
            "                                                                                          ";

    private static final String LOGOUT_COMMAND = "-logout";
    private static final String ERROR_COMMAND = "error";
    private static final String HELP_COMMAND = "-help";
    private static final String DASHBOARD_COMMAND = "-dashboard";
    private static final String TRANSFER_COMMAND = "-transfer";
    private static final String DEPOSIT_COMMAND = "-deposit";
    private static final String WITHDRAW_COMMAND = "-withdraw";
    private static final String CREATE_ACCOUNT_COMMAND = "-createAccount";
    private static final String CLOSE_ACCOUNT_COMMAND = "-closeAccount";
    private static final String HOME_COMMAND = "-home";

    public Session() {

    }

    public User signInOrRegisterUser(Scanner scanner) {
        final String LOG_IN_SIGN_UP_PROMPT = "Login (L) or Register (R) ";
        final String USERNAME_PROMPT = "Enter your username: ";

        Console console = System.console();
        UserInputHandler inputHandler = UserInputHandler.getInstance();
        String userOption = inputHandler.handleUserInput(scanner, LOG_IN_SIGN_UP_PROMPT,
                Arrays.asList("L", "R"));

        if (userOption.equals("L") || userOption.equals("l")) {
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

        System.out.println(WELCOME_TO_THE_CLI);

        User currUser = signInOrRegisterUser(scanner);

        if (currUser == null) {
            return LOGOUT_COMMAND;
        }

        System.out.println(WELCOME_USER_PROMPT_START + currUser.getUsername() + WELCOME_USER_PROMPT_END);

        System.out.print(LINE_PROMPT);
        String userInput = scanner.nextLine();

        while (!userInput.equals(LOGOUT_COMMAND) && !userInput.equals(SessionManager.EXIT_COMMAND)) {

            // Each if will call a seperate funtion like logout and login

            if (userInput.equals(HELP_COMMAND)) {
                System.out.println(getHelpCommands());
            } else if (userInput.equals(DASHBOARD_COMMAND)) {
                System.out.println("Dashboard");
            } else if (userInput.equals(CREATE_ACCOUNT_COMMAND)) {
                System.out.println("Create Account");
            } else if (userInput.equals(CLOSE_ACCOUNT_COMMAND)) {
                System.out.println("Close Account");
            } else if (userInput.equals(DEPOSIT_COMMAND)) {
                System.out.println("Deposit");
            } else if (userInput.equals(WITHDRAW_COMMAND)) {
                System.out.println("Withdraw");
            } else if (userInput.equals(TRANSFER_COMMAND)) {
                System.out.println("Transfer");
            } else if (userInput.equals(HOME_COMMAND)) {
                System.out.println("Home");
            } else {
                System.out.println("Invalid command. Type -help for a list of commands.");
            }

            System.out.print(LINE_PROMPT);
            userInput = scanner.nextLine();

        }

        if (userInput.equals(LOGOUT_COMMAND)) {
            return LOGOUT_COMMAND;
        }

        return SessionManager.EXIT_COMMAND;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getHelpCommands() {
        return "\nHere is a list of all available commands:\n" +
                "----------------------------------------\n" +
                LOGOUT_COMMAND + "\n" +
                HELP_COMMAND + "\n" +
                DASHBOARD_COMMAND + "\n" +
                CREATE_ACCOUNT_COMMAND + "\n" +
                CLOSE_ACCOUNT_COMMAND + "\n" +
                DEPOSIT_COMMAND + "\n" +
                WITHDRAW_COMMAND + "\n" +
                HELP_COMMAND + "\n" +
                SessionManager.EXIT_COMMAND + "\n" +
                TRANSFER_COMMAND;
    }
}
