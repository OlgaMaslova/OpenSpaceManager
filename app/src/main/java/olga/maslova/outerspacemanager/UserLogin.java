package olga.maslova.outerspacemanager;

/**
 * Created by omaslova on 16/01/2018.
 */

public class UserLogin {
    private String email;
    private String username;
    private String password;

    public UserLogin (String mail, String name, String password) {
        this.email = mail;
        this.username = name;
        this.password = password;
    }

    public UserLogin (String name, String password) {
        this.username = name;
        this.password = password;
    }
}
