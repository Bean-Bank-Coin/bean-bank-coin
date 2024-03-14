package request;

import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.swing.text.AbstractDocument;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.models.Account;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionRequest {
    private static TransactionRequest transactionRequest = null;

    public void withDraw(BigDecimal withdrawAmount, int accountID) {

        JSONObject payload = new JSONObject();
        payload.put("senderID", accountID);
        payload.put("receiverID", accountID);
        payload.put("transactionTypeID", 2);
        payload.put("transactionAmount", withdrawAmount);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        payload.put("transactionTimestamp", formattedDateTime);

        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("transaction", RequestType.PUT,
                Optional.of(payload));
    }

    public void deposit(BigDecimal depositAmount, int accountID) {

        JSONObject payload = new JSONObject();
        payload.put("senderID", accountID);
        payload.put("receiverID", accountID);
        payload.put("transactionTypeID", 1);
        payload.put("transactionAmount", depositAmount);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        payload.put("transactionTimestamp", formattedDateTime);

        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("transaction", RequestType.PUT,
                Optional.of(payload));

    }

    public void transfer(BigDecimal amount, int senderID, int receiverID) {

        JSONObject payload = new JSONObject();
        payload.put("senderID", senderID);
        payload.put("receiverID", receiverID);
        payload.put("transactionTypeID", 3);
        payload.put("transactionAmount", amount);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        payload.put("transactionTimestamp", formattedDateTime);

        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("transaction", RequestType.PUT,
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
