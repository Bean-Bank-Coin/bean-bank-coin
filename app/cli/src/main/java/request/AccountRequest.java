package request;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.swing.text.AbstractDocument;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.models.Account;

public class AccountRequest {
    private static AccountRequest accountRequest = null;

    public List<Account> getAccounts(int userID) throws JSONException {

        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("accounts/" + String.valueOf(userID), RequestType.GET,
                null);

        if (response.body().isEmpty())
            return Collections.emptyList();

        System.out.println(response.body());
        JSONArray userAccountsJson = new JSONArray(response.body());
        List<Account> userAccounts = new ArrayList<>();

        for (int i = 0; i < userAccountsJson.length(); i++) {
            JSONObject accountJson = userAccountsJson.getJSONObject(i);
            Account account = new Account(
                    accountJson.getInt("accountID"),
                    accountJson.getInt("userID"),
                    accountJson.getInt("beanTypeID"),
                    accountJson.getBigDecimal("balanceAmount"),
                    accountJson.getBoolean("closed"));
            userAccounts.add(account);
        }

        return userAccounts;
    }

   public Optional<Account> createAccount(int userID, int beanTypeID, BigDecimal balanceAmount, boolean isClosed) {
        JSONObject payload = new JSONObject();
        payload.put("userID", userID);
        payload.put("beanTypeID", beanTypeID);
        payload.put("balanceAmount", balanceAmount);
        payload.put("isClosed", isClosed);


        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("account", RequestType.POST, Optional.of(payload));

        if (response.body().equals(""))
            return Optional.empty();

        JSONObject createAccountObject = new JSONObject(response.body());

        return Optional.of(new Account(
            createAccountObject.getInt("accountID"),
            createAccountObject.getInt("userID"),
            createAccountObject.getInt("beanTypeID"),
            createAccountObject.getBigDecimal("balanceAmount"),
            createAccountObject.getBoolean("isClosed")));
    }

    private AccountRequest() {
    }

    public static AccountRequest getInstance() {
        if (accountRequest == null) {
            accountRequest = new AccountRequest();
        }

        return accountRequest;
    }

    public boolean closeAccount(int userID, int accountID)
    {
        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("accounts/" + String.valueOf(userID), RequestType.GET,
                null);

        System.out.println(response.body());

        JSONArray userAccountsJson = new JSONArray(response.body());
        List<Account> userAccounts = new ArrayList<>();
        for (int i = 0; i < userAccountsJson.length(); i++) {
            JSONObject accountJson = userAccountsJson.getJSONObject(i);
            boolean isClosed = accountJson.getBoolean("isClosed");
            if(userAccounts.contains(accountJson.getInt("accountID")))
            {
                isClosed = false;
                return false;
            }
            else
            {
                return true;
            }
    }

    }
}
