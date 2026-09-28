package controller.AdminControllers;

import exceptions.DataFileNotFoundException;
import model.Admin;
import service.FileHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Lets the logged-in Admin edit their own fullName, email, and phone.
 * Finds their existing line in admins.txt (matched by userId) and rewrites
 * just that line, leaving everyone else's row untouched.
 */
public class EditProfileScreen extends JFrame {

    private static final String FILE_PATH = "data/admins.txt";

    private Admin loggedInAdmin;
    private JTextField fullNameField, emailField, phoneField;

    public EditProfileScreen(Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;

        setTitle("Edit My Profile");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        fullNameField = new JTextField(loggedInAdmin.getFullName());
        emailField = new JTextField(loggedInAdmin.getEmail());
        phoneField = new JTextField(loggedInAdmin.getPhoneNumber());

        add(new JLabel("Full Name:"));
        add(fullNameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Phone:"));
        add(phoneField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> handleSave());
        add(new JLabel()); // filler for grid alignment
        add(saveButton);
    }

    private void handleSave() {
        String newFullName = fullNameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String newPhone = phoneField.getText().trim();

        if (newFullName.isEmpty() || newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full Name and Email cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<String> lines = FileHelper.readAllLines(FILE_PATH);
            boolean found = false;

            for (int i = 0; i < lines.size(); i++) {
                String[] p = lines.get(i).split(",");
                if (p.length >= 6 && p[0].equals(loggedInAdmin.getUserId())) {
                    // Rebuild this one line with the updated fields, keeping
                    // userId, username, password, and department unchanged.
                    String department = p.length > 6 ? p[6] : "";
                    String updatedLine = String.join(",",
                            p[0], p[1], p[2], newFullName, newEmail, newPhone, department);
                    lines.set(i, updatedLine);
                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Could not find your record in the data file.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = FileHelper.writeAllLines(FILE_PATH, lines);
            if (success) {
                loggedInAdmin.updateOwnProfile(newFullName, newEmail, newPhone); // keep in-memory object in sync too
                JOptionPane.showMessageDialog(this, "Profile updated successfully.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save changes.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (DataFileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Could not read the data file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
