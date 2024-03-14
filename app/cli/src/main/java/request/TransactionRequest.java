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

public class TransactionRequest {
    private static TransactionRequest transactionRequest = null;

    public void withDraw(BigDecimal balance, int accountID) {

        JSONObject payload = new JSONObject();
        payload.put("balanceAmount", balance);

        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("accounts/" + String.valueOf(accountID), RequestType.PUT,
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
