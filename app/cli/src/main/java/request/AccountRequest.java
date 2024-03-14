package request;

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

    private AccountRequest() {
    }

    public static AccountRequest getInstance() {
        if (accountRequest == null) {
            accountRequest = new AccountRequest();
        }

        return accountRequest;
    }

}
