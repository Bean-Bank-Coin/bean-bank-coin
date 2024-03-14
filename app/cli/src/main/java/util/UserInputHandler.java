package util;

import java.util.List;
import java.util.Scanner;

public class UserInputHandler {
    private static UserInputHandler userInputHandler = null;

    private UserInputHandler() {

    }

    public String handleUserInput(Scanner scanner, String prompt, List<String> lowercaseChoices) {
        Boolean invalidInput = true;

        System.out.print(prompt);
        String userInput = scanner.nextLine();

        while (invalidInput) {
            if (lowercaseChoices.contains(userInput.toLowerCase())) {
                invalidInput = false;
            } else if (!lowercaseChoices.isEmpty()) {
                System.out.println("Invalid option");
                System.out.print(prompt);
                userInput = scanner.nextLine();
            } else {
                invalidInput = false;
            }
        }

        if (lowercaseChoices.isEmpty()) {
            return userInput;
        }
        return userInput.toLowerCase();
    }

    public static UserInputHandler getInstance() {
        if (userInputHandler == null) {
            userInputHandler = new UserInputHandler();
        }

        return userInputHandler;
    }
}
