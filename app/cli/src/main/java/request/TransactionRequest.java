package request;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.json.JSONObject;

public class TransactionRequest {
    private static TransactionRequest transactionRequest = null;

    public static String token = "";

    public void withDraw(BigDecimal withdrawAmount, int accountID, int userID) {

        JSONObject payload = new JSONObject();
        payload.put("senderID", accountID);
        payload.put("receiverID", accountID);
        payload.put("transactionTypeID", 2);
        payload.put("transactionAmount", withdrawAmount);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        payload.put("transactionTimestamp", formattedDateTime);

        Request request = new Request(token);
        HttpResponse<String> response = request.makeRequest("transaction/" + String.valueOf(userID), RequestType.PUT,
                Optional.of(payload));
    }

    public void deposit(BigDecimal depositAmount, int accountID, int userID) {

        JSONObject payload = new JSONObject();
        payload.put("senderID", accountID);
        payload.put("receiverID", accountID);
        payload.put("transactionTypeID", 1);
        payload.put("transactionAmount", depositAmount);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        payload.put("transactionTimestamp", formattedDateTime);

        Request request = new Request(token);
        HttpResponse<String> response = request.makeRequest("transaction/" + String.valueOf(userID), RequestType.PUT,
                Optional.of(payload));

    }

    public void transfer(BigDecimal amount, int senderID, int receiverID, int userID) {

        JSONObject payload = new JSONObject();
        payload.put("senderID", senderID);
        payload.put("receiverID", receiverID);
        payload.put("transactionTypeID", 3);
        payload.put("transactionAmount", amount.round(new MathContext(2, RoundingMode.HALF_UP)));
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        payload.put("transactionTimestamp", formattedDateTime);

        Request request = new Request(token);
        HttpResponse<String> response = request.makeRequest("transaction/" + String.valueOf(userID), RequestType.PUT,
                Optional.of(payload));
    }

    private TransactionRequest() {
    }

    public static TransactionRequest getInstance() {
        if (transactionRequest == null) {
            transactionRequest = new TransactionRequest();
        }

        return transactionRequest;
    }

}
