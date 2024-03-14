package request;

import com.models.;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DashboardRequest {
    private static DashboardRequest dashboardRequest = null;

    public List<Account> getAccounts(int userID) {
        Request request = new Request();
        HttpResponse<String> response = request.makeRequest("accounts/" + userID, RequestType.GET, null);

        JSONArray accountArray = new JSONArray(response.body());
        List<Account> accountDetails = new ArrayList<>();
        for (Object account : accountArray) {
            JSONObject accountObj = new JSONObject(account);
            Account acc = new Account(
            accountObj.getInt("accountID"),
            accountObj.getInt("beanTypeID"),
            accountObj.getBigDecimal("balanceAmount"),
            accountObj.getBoolean("isClosed"),
            LocalDateTime.parse(accountObj.getString("dateCreated")),
            userID);

           //constructor account changed
            //add to the account to the list...
        }
        return accountDetails;
    };

    public DashboardRequest() {

    }

    public static DashboardRequest getInstance() {
        if (dashboardRequest == null) {
            dashboardRequest = new DashboardRequest();
        }

        return dashboardRequest;
    }
}
