package controller;

import model.User;
import service.AuthService;
import service.UserFileLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeDropdown;
    private AuthService authService;

    public LoginFrame() {
        // Load users.txt into memory and hand it to AuthService
        Map<String, User> userDirectory = UserFileLoader.loadUsers("data/users.txt");
        authService = new AuthService(userDirectory);

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

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String selectedType = (String) userTypeDropdown.getSelectedItem();

        User loggedInUser = authService.login(username, password);

        if (loggedInUser == null) {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check the selected role matches the account's actual role
        if (!loggedInUser.getRole().equals(selectedType)) {
            JOptionPane.showMessageDialog(this,
                    "This account is registered as " + loggedInUser.getRole() + ", not " + selectedType + ".",
                    "Role Mismatch", JOptionPane.ERROR_MESSAGE);
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
