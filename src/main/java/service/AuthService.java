package service;

import model.User;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * Handles login/logout for ALL roles. Kept separate from User itself
 * (single-responsibility: User = "who I am", AuthService = "how I get in").
 *
 * userDirectory should be loaded at app startup from users.txt by whoever
 * builds the data-loading layer (keyed by username -> User object).
 */
public class AuthService {

    private Map<String, User> userDirectory;

    public AuthService(Map<String, User> userDirectory) {
        this.userDirectory = userDirectory;
    }

    public User login(String username, String password) {
        User user = userDirectory.get(username);
//        if(user != null &&  user.checkPassword(passowrd)){
//            JOptionPane.showMessageDialog(null, "Login Successful", "success",JOptionPane.INFORMATION_MESSAGE);
//        }
//        else if(user ==  null){        
//            JOptionPane.showMessageDialog(null, "User dont exsist!", "Faild", JOptionPane.ERROR_MESSAGE); 
//        }
        if (user ==  null){System.out.println("User dont exsist!");}
        else if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null; // caller (GUI) should show "invalid credentials"
    }

    public void logout(User user) {
        // Session/state cleanup is handled by the GUI controller that calls this.
        System.out.println(user.getUsername() + " logged out.");
    }

    private boolean validateCredentials(String username, String password) {
        return login(username, password) != null;
    }
}
