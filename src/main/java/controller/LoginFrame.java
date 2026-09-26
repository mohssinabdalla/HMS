package controller;

import exceptions.DataFileNotFoundException;
import model.User;
import service.AuthService;
import service.UserFileLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeDropdown;
    private AuthService authService;

    public LoginFrame() {
        authService = buildAuthService();

        setTitle("Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(60, 20, 60)); // purple background, matching reference
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        JLabel userLabel = new JLabel("User Name");
        userLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(passwordField, gbc);

        JLabel typeLabel = new JLabel("User Type");
        typeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(typeLabel, gbc);

        userTypeDropdown = new JComboBox<>(new String[]{"Admin", "Doctor", "Patient", "MedicalManager"});
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(userTypeDropdown, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(e -> handleLogin());

        add(panel);
    }

    /**
     * Loads all 4 roles files into one combined structure and builds AuthService.
     * Each role's file is loaded independently, so if one file is missing, we still
     * want the app to start (with that one role unable to log in) rather than crash
     * entirely — so each load is wrapped in its own try-catch.
     */
    private AuthService buildAuthService(){
        Map<String, Map<String, User>> allUsers = new HashMap<>();

        loadRoleSafely(allUsers, "D:\\Univesity\\Semester 3\\OODJ\\Assignment\\HMS\\src\\main\\data\\admins.txt", "Admin");
        loadRoleSafely(allUsers, "data/doctors.txt", "Doctor");
        loadRoleSafely(allUsers, "data/patients.txt", "Patient");
        loadRoleSafely(allUsers, "data/medicalManagers.txt", "MedicalManager");

        return new AuthService(allUsers);
    }

    private void loadRoleSafely(Map<String, Map<String, User>> allUsers, String filePath, String role){
        try {
            Map<String, User> roleMap = UserFileLoader.loadUsersFromFile(filePath, role);
            allUsers.put(role, roleMap);
        } catch (DataFileNotFoundException e) {
            // Missing file for this role — log it, but let the app keep starting.
            // Logging in as this role just won't work until the file exists.
            System.out.println("Warning: " + e.getMessage());
            allUsers.put(role, new HashMap<>()); // empty map, not null — avoids NullPointerException later
        }
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String selectedType = (String) userTypeDropdown.getSelectedItem();

        // login() now only searches inside the selected role's data — no separate
        // role-mismatch check needed afterward, since a wrong role simply won't
        // find the username at all within that role's map.
        User loggedInUser = authService.login(username, password, selectedType);

        if (loggedInUser == null) {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password for the selected user type.",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Welcome, " + loggedInUser.getFullName() + " (" + loggedInUser.getRole() + ")!");
        // TODO: open the correct role-specific screen here, e.g.:
        // if (loggedInUser instanceof Admin) { new AdminDashboard((Admin) loggedInUser).setVisible(true); }
        // this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}