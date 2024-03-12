package session;

import com.models.Account;
import com.models.BeanType;
import com.models.User;

import java.util.List;

public class Session {
    private User currentUser;
    private List<BeanType> beanTypes;

    private List<Account> userAccounts;


    public Session() {

    }
}
