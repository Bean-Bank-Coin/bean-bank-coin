package session;

import java.io.Console;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
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
import request.Request;
import request.RequestType;
import request.TransactionRequest;
import util.Auth;
import util.UserInputHandler;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class Session {
    private static final String LINE_PROMPT = "> ";
    private static final String WELCOME_USER_PROMPT_START = "Welcome, ";
    private static final String WELCOME_USER_PROMPT_END = "!";
    private static final String LOGOUT_COMMAND = "-logout";
    private static final String HELP_COMMAND = "-help";
    private static final String DASHBOARD_COMMAND = "-dashboard";
    private static final String TRANSFER_COMMAND = "-transfer";
    private static final String DEPOSIT_COMMAND = "-deposit";
    private static final String BACK_COMMAND = "-back";
    private static final String WITHDRAW_COMMAND = "-withdraw";
    private static final String CREATE_ACCOUNT_COMMAND = "-createAccount";
    private static final String CLOSE_ACCOUNT_COMMAND = "-closeAccount";
    private static final String HOME_COMMAND = "-home";
    private String token;
    private User user;

    public Session(Scanner scanner) {
        Auth auth = new Auth();
        this.token = auth.runAuth(scanner);
        String username = auth.getUsername(token);
        Request request = new Request(this.token);
        HttpResponse<String> response = request.makeRequest("user/" + username, RequestType.GET, Optional.empty());
        JSONObject jsonResponse = new JSONObject(response.body());
        this.user = new User();
        this.user.setUserID(jsonResponse.getInt("userID"));
        this.user.setUsername(jsonResponse.getString("username"));

        TransactionRequest.token = this.token;
        AccountRequest.token = this.token;
    }

    public String startSession(Scanner scanner) {
        System.out.println(WELCOME_USER_PROMPT_START + this.user.getUsername() + WELCOME_USER_PROMPT_END);
        System.out.print(LINE_PROMPT);
        String userInput = scanner.nextLine();

        while (!userInput.equals(LOGOUT_COMMAND) && !userInput.equals(SessionManager.EXIT_COMMAND)) {
            navigator(this.user, userInput, scanner);
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
            createAccount(scanner);
        } else if (userInput.equals(CLOSE_ACCOUNT_COMMAND)) {
            closeAccount(currUser.getUserID(), scanner);
        } else if (userInput.equals(DEPOSIT_COMMAND)) {
            deposit(scanner, currUser);
        } else if (userInput.equals(WITHDRAW_COMMAND)) {
            withDraw(scanner, currUser);
        } else if (userInput.equals(TRANSFER_COMMAND)) {
            transfer(scanner, currUser);
        } else if (userInput.equals(HOME_COMMAND)) {
        } else {
            System.out.println("Invalid command. Type -help for a list of commands.");
        }
    }

    public void createAccount(Scanner scanner) {
        AccountRequest createNewAccount = AccountRequest.getInstance();
        System.out.println("Enter the Bean Type ID you would like to use: ");
        int beantypeId = Integer.parseInt(scanner.nextLine());
        BigDecimal balanceAmt = new BigDecimal(50.00);
        createNewAccount.createAccount(beantypeId, balanceAmt, true, user);
    }

    public void closeAccount(int userID, Scanner scanner) {
        AccountRequest Account = AccountRequest.getInstance();
        System.out.println("Please specify the account ID you would like to close: ");
        int accID = scanner.nextInt();
        if (Account.closeAccount(userID, accID) == false) {
            System.out.println("Account closed successfully.");
        } else {
            System.out.println("Account can not be closed");
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
                    "Account " + userAccount.getAccountID() + " has balance R"
                            + convertToRands(userAccount.getBalanceAmount(), userAccount.getBeanTypeID()));
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
                    if (convertToRands(userAccount.getBalanceAmount(), userAccount.getBeanTypeID())
                            .compareTo(inputAmount) >= 0
                            && userAccount.getAccountID() == Integer.parseInt(account)) {

                        currBalance = convertToRands(userAccount.getBalanceAmount(), userAccount.getBeanTypeID());
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

        TransactionRequest.getInstance().transfer(amount, Integer.parseInt(account), Integer.parseInt(recieverAccount),
                user.getUserID());
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
                    "Account " + userAccount.getAccountID() + " has balance R"
                            + convertToRands(userAccount.getBalanceAmount(), userAccount.getBeanTypeID()));
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
                    if (convertToRands(userAccount.getBalanceAmount(), userAccount.getBeanTypeID())
                            .compareTo(inputAmount) >= 0
                            && userAccount.getAccountID() == Integer.parseInt(account)) {

                        currBalance = convertToRands(userAccount.getBalanceAmount(), userAccount.getBeanTypeID());
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
        TransactionRequest.getInstance().withDraw(amount, Integer.parseInt(account), user.getUserID());
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
                    "Account " + userAccount.getAccountID() + " has balance R"
                            + convertToRands(userAccount.getBalanceAmount(), userAccount.getBeanTypeID()));
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

        TransactionRequest.getInstance().deposit(amount, Integer.parseInt(account), user.getUserID());
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
                    System.out.println("Bean Type: " + getBeanName(acc.getBeanTypeID()));
                    System.out.println("Number of Beans: " + acc.getBalanceAmount());
                    System.out.print(
                            "Balance Amount: " + "R" + convertToRands(acc.getBalanceAmount(), acc.getBeanTypeID())
                                    + "\n");
                }
            }
        } else {
            System.out.println("No accounts to display.");
        }
    }

    public static BigDecimal convertToRands(BigDecimal amount, int beanTypeID) {
        BigDecimal valueInRands = getValueInRands(beanTypeID);
        if (valueInRands == null) {
            System.out.println("Invalid beanTypeID");
            return BigDecimal.ZERO;
        }
        return amount.multiply(valueInRands);
    }

    private static BigDecimal getValueInRands(int beanTypeID) {
        switch (beanTypeID) {
            case 1:
                return new BigDecimal("5.00");
            case 2:
                return new BigDecimal("10.00");
            case 3:
                return new BigDecimal("15.00");
            case 4:
                return new BigDecimal("20.00");
            case 5:
                return new BigDecimal("25.00");
            case 6:
                return new BigDecimal("30.00");
            case 7:
                return new BigDecimal("35.00");
            case 8:
                return new BigDecimal("40.00");
            default:
                return null;
        }
    }

    public static String getBeanName(int beanTypeID) {
        switch (beanTypeID) {
            case 1:
                return "Green Bean";
            case 2:
                return "Blue Bean";
            case 3:
                return "Purple Bean";
            case 4:
                return "Orange Bean";
            case 5:
                return "Red Bean";
            case 6:
                return "Yellow Bean";
            case 7:
                return "Silver Bean";
            case 8:
                return "Gold Bean";
            default:
                return "Bean not found";
        }
    }

}
