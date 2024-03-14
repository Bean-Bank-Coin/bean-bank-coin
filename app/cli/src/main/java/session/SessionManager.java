package session;

import com.models.TransactionsType;
import request.Request;
import request.RequestType;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class SessionManager {

    private Request request;

    public static final String EXIT_COMMAND = "-exit";

    private static final String WELCOME_TO_THE_CLI = " __    __    ___  _         __   ___   ___ ___    ___      ______   ___                   \r\n"
            + //
            "|  T__T  T  /  _]| T       /  ] /   \\ |   T   T  /  _]    |      T /   \\                  \r\n" + //
            "|  |  |  | /  [_ | |      /  / Y     Y| _   _ | /  [_     |      |Y     Y                 \r\n" + //
            "|  |  |  |Y    _]| l___  /  /  |  O  ||  \\_/  |Y    _]    l_j  l_j|  O  |                 \r\n" + //
            "l  `  '  !|   [_ |     T/   \\_ |     ||   |   ||   [_       |  |  |     |                 \r\n" + //
            " \\      / |     T|     |\\     |l     !|   |   ||     T      |  |  l     !                 \r\n" + //
            "  \\_/\\_/  l_____jl_____j \\____j \\___/ l___j___jl_____j      l__j   \\___/                  \r\n" + //
            "                                                                                          \r\n" + //
            " ____     ___   ____  ____       ____    ____  ____   __  _         __   ___  ____  ____  \r\n" + //
            "|    \\   /  _] /    T|    \\     |    \\  /    T|    \\ |  l/ ]       /  ] /   \\l    j|    \\ \r\n" + //
            "|  o  ) /  [_ Y  o  ||  _  Y    |  o  )Y  o  ||  _  Y|  ' /       /  / Y     Y|  T |  _  Y\r\n" + //
            "|     TY    _]|     ||  |  |    |     T|     ||  |  ||    \\      /  /  |  O  ||  | |  |  |\r\n" + //
            "|  O  ||   [_ |  _  ||  |  |    |  O  ||  _  ||  |  ||     Y    /   \\_ |     ||  | |  |  |\r\n" + //
            "|     ||     T|  |  ||  |  |    |     ||  |  ||  |  ||  .  |    \\     |l     !j  l |  |  |\r\n" + //
            "l_____jl_____jl__j__jl__j__j    l_____jl__j__jl__j__jl__j\\_j     \\____j \\___/|____jl__j__j\r\n" + //
            "                                                                                          \r\n" + //
            "                                                                                          \r\n" + //
            "                                                                                          ";

    private static final String GOODBYE_COMMAND = " ______  __ __   ____  ____   __  _      __ __   ___   __ __      _____   ___   ____      \r\n"
            + //
            "|      T|  T  T /    T|    \\ |  l/ ]    |  T  T /   \\ |  T  T    |     | /   \\ |    \\     \r\n" + //
            "|      ||  l  |Y  o  ||  _  Y|  ' /     |  |  |Y     Y|  |  |    |   __jY     Y|  D  )    \r\n" + //
            "l_j  l_j|  _  ||     ||  |  ||    \\     |  ~  ||  O  ||  |  |    |  l_  |  O  ||    /     \r\n" + //
            "  |  |  |  |  ||  _  ||  |  ||     Y    l___, ||     ||  :  |    |   _] |     ||    \\     \r\n" + //
            "  |  |  |  |  ||  |  ||  |  ||  .  |    |     !l     !l     |    |  T   l     !|  .  Y    \r\n" + //
            "  l__j  l__j__jl__j__jl__j__jl__j\\_j    l____/  \\___/  \\__,_j    l__j    \\___/ l__j\\_j    \r\n" + //
            "                                                                                          \r\n" + //
            " ____    ____  ____   __  _  ____  ____    ____      __    __  ____  ______  __ __        \r\n" + //
            "|    \\  /    T|    \\ |  l/ ]l    j|    \\  /    T    |  T__T  Tl    j|      T|  T  T       \r\n" + //
            "|  o  )Y  o  ||  _  Y|  ' /  |  T |  _  YY   __j    |  |  |  | |  T |      ||  l  |       \r\n" + //
            "|     T|     ||  |  ||    \\  |  | |  |  ||  T  |    |  |  |  | |  | l_j  l_j|  _  |       \r\n" + //
            "|  O  ||  _  ||  |  ||     Y |  | |  |  ||  l_ |    l  `  '  ! |  |   |  |  |  |  |       \r\n" + //
            "|     ||  |  ||  |  ||  .  | j  l |  |  ||     |     \\      /  j  l   |  |  |  |  |       \r\n" + //
            "l_____jl__j__jl__j__jl__j\\_j|____jl__j__jl___,_j      \\_/\\_/  |____j  l__j  l__j__j       \r\n" + //
            "                                                                                          \r\n" + //
            " ____     ___   ____  ____       ____    ____  ____   __  _         __   ___  ____  ____  \r\n" + //
            "|    \\   /  _] /    T|    \\     |    \\  /    T|    \\ |  l/ ]       /  ] /   \\l    j|    \\ \r\n" + //
            "|  o  ) /  [_ Y  o  ||  _  Y    |  o  )Y  o  ||  _  Y|  ' /       /  / Y     Y|  T |  _  Y\r\n" + //
            "|     TY    _]|     ||  |  |    |     T|     ||  |  ||    \\      /  /  |  O  ||  | |  |  |\r\n" + //
            "|  O  ||   [_ |  _  ||  |  |    |  O  ||  _  ||  |  ||     Y    /   \\_ |     ||  | |  |  |\r\n" + //
            "|     ||     T|  |  ||  |  |    |     ||  |  ||  |  ||  .  |    \\     |l     !j  l |  |  |\r\n" + //
            "l_____jl_____jl__j__jl__j__j    l_____jl__j__jl__j__jl__j\\_j     \\____j \\___/|____jl__j__j\r\n" + //
            "                                                                                          ";

    public SessionManager() {
        request = new Request();
        HttpResponse<String> response = request.makeRequest("transactionsType", RequestType.GET, null);
    }

    public void startSession() {
        System.out.println(WELCOME_TO_THE_CLI);
        Scanner scanner = new Scanner(System.in);
        Session session = new Session();
        String exitCommand = session.startSession(scanner);

        while (!exitCommand.equals(EXIT_COMMAND)) {
            exitCommand = session.startSession(scanner);
        }

        System.out.println(GOODBYE_COMMAND);
        scanner.close();
    }
}