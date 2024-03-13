package util;

import java.util.List;
import java.util.Scanner;

public class UserInputHandler {
    private static UserInputHandler userInputHandler = null;

    private UserInputHandler() {

    }

    public String handleUserInput(Scanner scanner, String prompt, List<String> uppercaseChoices) {
        Boolean invalidInput = true;

        System.out.print(prompt);
        String userInput = scanner.nextLine();

        while (invalidInput) {
            if (uppercaseChoices.contains(userInput.toUpperCase())) {
                invalidInput = false;
            } else if (!uppercaseChoices.isEmpty()) {
                System.out.println("Invalid option");
                System.out.print(prompt);
                userInput = scanner.nextLine();
            } else {
                invalidInput = false;
            }
        }

        return userInput;
    }

    public static UserInputHandler getInstance() {
        if (userInputHandler == null) {
            userInputHandler = new UserInputHandler();
        }

        return userInputHandler;
    }
}
