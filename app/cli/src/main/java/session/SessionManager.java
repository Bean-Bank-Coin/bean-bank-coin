package session;

import com.models.TransactionsType;
import request.Request;
import request.RequestType;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class SessionManager {

    private List<TransactionsType> transactionsTypes;

    private Request request;

    public static final String EXIT_COMMAND = "exit";

    public SessionManager() {
        request = new Request();
        HttpResponse response = request.makeRequest("transactionsType", RequestType.GET, null);
    }

    public void startSession() {
        Scanner scanner = new Scanner(System.in);

        Session session = new Session();
        String exitCommand = session.startSession(scanner);

        while (!exitCommand.equals(EXIT_COMMAND)) {
            exitCommand = session.startSession(scanner);
        }

        scanner.close();
    }
}