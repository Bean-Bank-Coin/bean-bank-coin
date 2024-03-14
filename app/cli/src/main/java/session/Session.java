package session;

import java.io.Console;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.google.common.hash.Hashing;
import com.models.Account;
import com.models.BeanType;
import com.models.User;

import request.AccountRequest;
import request.TransactionRequest;
import request.UserRequest;
import util.UserInputHandler;
import java.util.regex.Pattern;

public class Session {
    private User currentUser;

    private List<BeanType> beanTypes;

    private List<Account> userAccounts;

    private static final String LINE_PROMPT = "> ";
    private static final String WELCOME_USER_PROMPT_START = "Welcome, ";
    private static final String WELCOME_USER_PROMPT_END = "!";
    private static final String USER_NOT_FOUND_MESSAGE = "Username not found.";
    private static final String LOGOUT_COMMAND = "-logout";
    private static final String ERROR_COMMAND = "error";
    private static final String HELP_COMMAND = "-help";
    private static final String DASHBOARD_COMMAND = "-dashboard";
    private static final String TRANSFER_COMMAND = "-transfer";
    private static final String DEPOSIT_COMMAND = "-deposit";
    private static final String BACK_COMMAND = "-back";
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
                Arrays.asList("l", "r"));

        if (userOption.equalsIgnoreCase("l")) {
            String username = inputHandler.handleUserInput(scanner, USERNAME_PROMPT, Collections.emptyList());
            String password = new String(console.readPassword("Enter a password: "));
            password = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();

            Optional<User> userOptional = UserRequest.getInstance().getUser(username, Optional.empty());

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
        String password = new String(console.readPassword("Enter a password: "));
        password = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        Optional<User> userOptional = UserRequest.getInstance().getUser(username, Optional.of(email));

        while (userOptional.isPresent()) {
            if (userOptional.get().getUsername().equals(username)) {
                System.out.println("Username already exists");
                username = inputHandler.handleUserInput(scanner, USERNAME_PROMPT, Collections.emptyList());
            }

            if (userOptional.get().getEmail().equals(email)) {
                System.out.println("Email already exists");
                email = inputHandler.handleUserInput(scanner, "Enter your email: ", Collections.emptyList());
            }

            userOptional = UserRequest.getInstance().getUser(username, Optional.of(email));
        }

        return UserRequest.getInstance().createUser(username, password, email).get();
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

            navigator(currUser, userInput, scanner);
            System.out.print(LINE_PROMPT);
            userInput = scanner.nextLine();
        }

        if (userInput.equals(LOGOUT_COMMAND)) {
            return LOGOUT_COMMAND;
        }

        return SessionManager.EXIT_COMMAND;
    }

    public void navigator(User currUser, String userInput, Scanner scanner) {

        if (userInput.equals(HELP_COMMAND)) {
            System.out.println(getHelpCommands("all"));
        } else if (userInput.equals(DASHBOARD_COMMAND)) {
            getDashBoard(currUser.getUserID());
        } else if (userInput.equals(CREATE_ACCOUNT_COMMAND)) {
            System.out.println("Create Account");
        } else if (userInput.equals(CLOSE_ACCOUNT_COMMAND)) {
            System.out.println("Close Account");
        } else if (userInput.equals(DEPOSIT_COMMAND)) {
            deposit(scanner, currUser);
        } else if (userInput.equals(WITHDRAW_COMMAND)) {
            withDraw(scanner, currUser);
        } else if (userInput.equals(TRANSFER_COMMAND)) {
            transfer(scanner, currUser);
        } else if (userInput.equals(HOME_COMMAND)) {
            System.out.println("Home");
        } else {
            System.out.println("Invalid command. Type -help for a list of commands.");
        }
    }

    public void transfer(Scanner scanner, User currUser) {
        final String TRANSFER_PROMPT = "Enter the amount you would like to transfer in Rands (R)";
        final String ACCOUNT_PROMPT = "Enter the account you would like to transfer from: ";
        final String RECIEVER_ACCOUNT_PROMPT = "Enter the account you would like to transfer to: ";

        final String CONFIRMATION_PROMPT = "[Transfer confirmed]\n[Taking you back to the home page]\n";
        final String ENV_PROMPT = "Transfer > ";
        boolean amountEntered = false;
        BigDecimal amount;
        String account;
        BigDecimal currBalance = new BigDecimal(0);
        boolean accountValid = false;
        boolean ammountValid = false;

        String userInput = "";
        List<Account> userAccounts = AccountRequest.getInstance().getAccounts(currUser.getUserID());
        System.out.println("Your accounts are: \n------------------");

        for (Account userAccount : userAccounts) {
            System.out.println(
                    "Account " + userAccount.getAccountID() + " has balance R" + userAccount.getBalanceAmount());
        }

        System.out.println();
        System.out.println(ACCOUNT_PROMPT);

        while (!isWholeNumber(userInput) || !accountValid) {

            System.out.print(LINE_PROMPT + ENV_PROMPT);
            userInput = scanner.nextLine();

            if (isWholeNumber(userInput)) {
                for (Account userAccount : userAccounts) {
                    if (userAccount.getAccountID() == Integer.parseInt(userInput)) {
                        accountValid = true;
                        break;
                    }
                }
            }

            if (userInput.equals(BACK_COMMAND)) {
                return;
            }

            if (userInput.equals(HELP_COMMAND)) {
                System.out.println(getHelpCommands("transfer"));
            } else if (!accountValid) {
                System.out.println("Invalid account number. Try again or type -help for help");
            }

        }

        account = userInput;

        System.out.println(TRANSFER_PROMPT);

        while (!isNumber(userInput) || !ammountValid) {

            System.out.print(LINE_PROMPT + ENV_PROMPT);
            userInput = scanner.nextLine();

            if (isNumber(userInput)) {
                BigDecimal inputAmount = new BigDecimal(userInput);
                for (Account userAccount : userAccounts) {
                    if (userAccount.getBalanceAmount().compareTo(inputAmount) >= 0
                            && userAccount.getAccountID() == Integer.parseInt(account)) {

                        currBalance = userAccount.getBalanceAmount();
                        ammountValid = true;
                        break;
                    }
                }
            }

            if (userInput.equals(BACK_COMMAND)) {
                return;
            }
            if (userInput.equals(HELP_COMMAND)) {
                System.out.println(getHelpCommands("transfer"));
            } else if (!ammountValid) {
                System.out.println("Invalid amount. Try again or type -help for help");
            }

        }

        amount = new BigDecimal(userInput);

        System.out.println();
        System.out.println(RECIEVER_ACCOUNT_PROMPT);

        accountValid = false;

        while (!isWholeNumber(userInput) || !accountValid) {

            System.out.print(LINE_PROMPT + ENV_PROMPT);
            userInput = scanner.nextLine();

            if (isWholeNumber(userInput)) {
                // for (Account userAccount : userAccounts) {
                // if (userAccount.getAccountID() == Integer.parseInt(userInput)) {
                // accountValid = true;
                // break;
                // }
                // }

                accountValid = true;
            }

            if (userInput.equals(BACK_COMMAND)) {
                return;
            }

            if (userInput.equals(HELP_COMMAND)) {
                System.out.println(getHelpCommands("transfer"));
            } else if (!accountValid) {
                System.out.println("Invalid account number. Try again or type -help for help");
            }
        }

        String recieverAccount = userInput;

        TransactionRequest.getInstance().transfer(amount, Integer.parseInt(account), Integer.parseInt(recieverAccount));
        System.out.println(CONFIRMATION_PROMPT);
        return;

    }

    public void withDraw(Scanner scanner, User currUser) {
        final String WITHDRAW_PROMPT = "Enter the amount you would like to withdraw in Rands (R)";
        final String ACCOUNT_PROMPT = "Enter the account you would like to withdraw from: ";
        final String CONFIRMATION_PROMPT = "[Withdraw confirmed]\n[Taking you back to the home page]\n";
        final String ENV_PROMPT = "Withdraw > ";
        boolean amountEntered = false;
        BigDecimal amount;
        String account;
        BigDecimal currBalance = new BigDecimal(0);
        boolean accountValid = false;
        boolean ammountValid = false;

        String userInput = "";
        List<Account> userAccounts = AccountRequest.getInstance().getAccounts(currUser.getUserID());
        System.out.println("Your accounts are: \n------------------");

        for (Account userAccount : userAccounts) {
            System.out.println(
                    "Account " + userAccount.getAccountID() + " has balance R" + userAccount.getBalanceAmount());
        }

        System.out.println();
        System.out.println(ACCOUNT_PROMPT);

        while (!isWholeNumber(userInput) || !accountValid) {

            System.out.print(LINE_PROMPT + ENV_PROMPT);
            userInput = scanner.nextLine();

            if (isWholeNumber(userInput)) {
                for (Account userAccount : userAccounts) {
                    if (userAccount.getAccountID() == Integer.parseInt(userInput)) {
                        accountValid = true;
                        break;
                    }
                }
            }

            if (userInput.equals(BACK_COMMAND)) {
                return;
            }

            if (userInput.equals(HELP_COMMAND)) {
                System.out.println(getHelpCommands("withdraw"));
            } else if (!accountValid) {
                System.out.println("Invalid account number. Try again or type -help for help");
            }

        }

        account = userInput;

        System.out.println(WITHDRAW_PROMPT);

        while (!isNumber(userInput) || !ammountValid) {

            System.out.print(LINE_PROMPT + ENV_PROMPT);
            userInput = scanner.nextLine();

            if (isNumber(userInput)) {
                BigDecimal inputAmount = new BigDecimal(userInput);
                for (Account userAccount : userAccounts) {
                    if (userAccount.getBalanceAmount().compareTo(inputAmount) >= 0
                            && userAccount.getAccountID() == Integer.parseInt(account)) {

                        currBalance = userAccount.getBalanceAmount();
                        ammountValid = true;
                        break;
                    }
                }
            }

            if (userInput.equals(BACK_COMMAND)) {
                return;
            }
            if (userInput.equals(HELP_COMMAND)) {
                System.out.println(getHelpCommands("withdraw"));
            } else if (!ammountValid) {
                System.out.println("Invalid amount. Try again or type -help for help");
            }

        }

        amount = new BigDecimal(userInput);

        BigDecimal newBalance = currBalance.subtract(amount);
        TransactionRequest.getInstance().withDraw(newBalance, Integer.parseInt(account));
        System.out.println(CONFIRMATION_PROMPT);
        return;

    }

    public void deposit(Scanner scanner, User currUser) {
        final String DEPOSIT_PROMPT = "Enter the amount you would like to deposit in Rands (R)";
        final String ACCOUNT_PROMPT = "Enter the account you would like to deposit to: ";
        final String CONFIRMATION_PROMPT = "[Deposit confirmed]\n[Taking you back to the home page]\n";
        final String ENV_PROMPT = "Deposit > ";
        boolean amountEntered = false;
        BigDecimal amount;
        String account;
        BigDecimal currBalance = new BigDecimal(0);
        boolean accountValid = false;
        boolean ammountValid = false;

        String userInput = "";
        List<Account> userAccounts = AccountRequest.getInstance().getAccounts(currUser.getUserID());
        System.out.println("Your accounts are: \n------------------");

        for (Account userAccount : userAccounts) {
            System.out.println(
                    "Account " + userAccount.getAccountID() + " has balance R" + userAccount.getBalanceAmount());
        }

        System.out.println();
        System.out.println(ACCOUNT_PROMPT);

        while (!isWholeNumber(userInput) || !accountValid) {

            System.out.print(LINE_PROMPT + ENV_PROMPT);
            userInput = scanner.nextLine();

            if (isWholeNumber(userInput)) {
                for (Account userAccount : userAccounts) {
                    if (userAccount.getAccountID() == Integer.parseInt(userInput)) {
                        accountValid = true;
                        break;
                    }
                }
            }

            if (userInput.equals(BACK_COMMAND)) {
                return;
            }

            if (userInput.equals(HELP_COMMAND)) {
                System.out.println(getHelpCommands("deposit"));
            } else if (!accountValid) {
                System.out.println("Invalid account number. Try again or type -help for help");
            }

        }

        account = userInput;

        System.out.println(DEPOSIT_PROMPT);

        while (!isNumber(userInput) || !ammountValid) {

            System.out.print(LINE_PROMPT + ENV_PROMPT);
            userInput = scanner.nextLine();

            if (isNumber(userInput)) {
                ammountValid = true;
            }

            if (userInput.equals(BACK_COMMAND)) {
                return;
            }
            if (userInput.equals(HELP_COMMAND)) {
                System.out.println(getHelpCommands("deposit"));
            } else if (!ammountValid) {
                System.out.println("Invalid amount. Try again or type -help for help");
            }
        }

        amount = new BigDecimal(userInput);

        TransactionRequest.getInstance().deposit(amount, Integer.parseInt(account));
        System.out.println(CONFIRMATION_PROMPT);
        return;

    }

    public static boolean isNumber(String input) {
        String numberPattern = "-?\\d+(\\.\\d+)?";
        Pattern pattern = Pattern.compile(numberPattern);
        return pattern.matcher(input).matches();
    }

    public static boolean isWholeNumber(String input) {
        String wholeNumberPattern = "-?\\d+";
        Pattern pattern = Pattern.compile(wholeNumberPattern);
        return pattern.matcher(input).matches();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getHelpCommands(String option) {
        if (option.equals("all")) {
            return "\nHere is a list of all available commands:\n" +
                    "----------------------------------------\n" +
                    LOGOUT_COMMAND + "\n" +
                    HELP_COMMAND + "\n" +
                    DASHBOARD_COMMAND + "\n" +
                    CREATE_ACCOUNT_COMMAND + "\n" +
                    CLOSE_ACCOUNT_COMMAND + "\n" +
                    DEPOSIT_COMMAND + "\n" +
                    WITHDRAW_COMMAND + "\n" +
                    SessionManager.EXIT_COMMAND + "\n" +
                    BACK_COMMAND + "\n" +
                    TRANSFER_COMMAND + "\n";
        }

        if (option.equals("withdraw")) {
            return "\nHere is a list of all available commands:\n" +
                    "----------------------------------------\n" +
                    BACK_COMMAND + "\n" +
                    HELP_COMMAND + "\n";
        }

        if (option.equals("deposit")) {
            return "\nHere is a list of all available commands:\n" +
                    "----------------------------------------\n" +
                    BACK_COMMAND + "\n" +
                    HELP_COMMAND + "\n";
        }

        if (option.equals("transfer")) {
            return "\nHere is a list of all available commands:\n" +
                    "----------------------------------------\n" +
                    BACK_COMMAND + "\n" +
                    HELP_COMMAND + "\n";
        }

        if (option.equals("create account")) {
            return "\nHere is a list of all available commands:\n" +
                    "----------------------------------------\n" +
                    BACK_COMMAND + "\n" +
                    HELP_COMMAND + "\n";
        }

        if (option.equals("delete account")) {
            return "\nHere is a list of all available commands:\n" +
                    "----------------------------------------\n" +
                    BACK_COMMAND + "\n" +
                    HELP_COMMAND + "\n";
        }

        return ("\nHere is a list of all available commands:\n" +
                "----------------------------------------\n" +
                LOGOUT_COMMAND + "\n" +
                HELP_COMMAND + "\n" +
                DASHBOARD_COMMAND + "\n" +
                CREATE_ACCOUNT_COMMAND + "\n" +
                CLOSE_ACCOUNT_COMMAND + "\n" +
                DEPOSIT_COMMAND + "\n" +
                WITHDRAW_COMMAND + "\n" +
                BACK_COMMAND + "\n" +
                SessionManager.EXIT_COMMAND + "\n" +
                TRANSFER_COMMAND + "\n");
    }

    public void getDashBoard(int userId) {
        AccountRequest dashDisplay = AccountRequest.getInstance();
        List<Account> accountList = dashDisplay.getAccounts(userId);
        if (!accountList.isEmpty()) {
            for (Account acc : accountList) {
                if (!acc.getClosed()) {
                    System.out.print("\nAccount " + acc.getAccountID() + " details: \n---------------------\n");
                    System.out.print("ID:" + acc.getAccountID());
                    System.out.print(", Bean Type ID:" + acc.getBeanTypeID());
                    System.out.print(", Balance Amount:" + acc.getBalanceAmount() + "\n");
                }
            }
        } else {
            System.out.println("No accounts to display.");
        }
    }

}
