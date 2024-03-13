import session.SessionManager;

public class Main {
    public static void main(String[] args) {
        SessionManager sessionManager = new SessionManager();

        while (true) {
            sessionManager.startSession();
        }
    }
}
