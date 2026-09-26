package controller.AdminControllers;

import exceptions.DataFileNotFoundException;
import model.Admin;
import service.FileHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Landing screen after a successful Admin login. Shows a count per role with
 * a "Manage X" button (drills into that role's screen — not built yet, hence
 * the TODOs), plus Admin ' s own quick actions along the bottom.
 */
public class AdminDashboard_1 extends JFrame {

    private Admin loggedInAdmin;

    public AdminDashboard_1(Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;

        setTitle("Admin Dashboard");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buildWelcomePanel(), BorderLayout.NORTH);
        add(buildRoleCardsPanel(), BorderLayout.CENTER);
        add(buildQuickActionsPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildWelcomePanel() {
        JPanel panel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome, " + loggedInAdmin.getFullName() + " (Admin)");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcomeLabel);
        return panel;
    }

    private JPanel buildRoleCardsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panel.add(buildRoleCard("Admins", "data/admins.txt", "Manage Admins"));
        panel.add(buildRoleCard("Doctors", "data/doctors.txt", "Manage Doctors"));
        panel.add(buildRoleCard("Patients", "data/patients.txt", "Manage Patients"));
        panel.add(buildRoleCard("Medical Managers", "data/medicalManagers.txt", "Manage Medical Managers"));

        return panel;
    }

    /**
     * One "card" = role name, live count read from that role's file, and a
     * Manage button. Count reads via FileHelper — 0 shown if the file is
     * missing or empty, rather than crashing the dashboard.
     */
    private JPanel buildRoleCard(String roleLabel, String filePath, String buttonLabel) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        int count = countUsersInFile(filePath);

        JLabel titleLabel = new JLabel(roleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel countLabel = new JLabel(String.valueOf(count));
        countLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton manageButton = new JButton(buttonLabel);
        manageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageButton.addActionListener(e -> openManageScreen(roleLabel));

        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(countLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(manageButton);
        card.add(Box.createVerticalStrut(10));

        return card;
    }

    private int countUsersInFile(String filePath) {
        try {
            List<String> lines = FileHelper.readAllLines(filePath);
            return lines.size();
        } catch (DataFileNotFoundException e) {
            return 0; // file doesn't exist yet — treat as zero users, not an error on this screen
        }
    }

    /**
     * TODO: replace this placeholder with the actual role-specific management
     * screen (table + text fields + Add/Edit/Delete, per the fruit-table
     * design reference) once each role's screen is built.
     */
    private void openManageScreen(String roleLabel) {
        JOptionPane.showMessageDialog(this,
                "The \"" + roleLabel + "\" management screen isn't built yet — coming soon.",
                "Not Implemented", JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel buildQuickActionsPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton viewLogButton = new JButton("View Activity Log");
        viewLogButton.addActionListener(e -> showActivityLog());

        JButton allocateAssetButton = new JButton("Allocate Hospital Asset");
        allocateAssetButton.addActionListener(e -> notImplementedYet("Allocate Hospital Asset"));

        JButton configureRateButton = new JButton("Configure Consultation Rate");
        configureRateButton.addActionListener(e -> notImplementedYet("Configure Consultation Rate"));

        JButton manageInsuranceButton = new JButton("Manage Insurance Network");
        manageInsuranceButton.addActionListener(e -> notImplementedYet("Manage Insurance Network"));

        JButton assignDoctorButton = new JButton("Assign Doctor to Manager");
        assignDoctorButton.addActionListener(e -> notImplementedYet("Assign Doctor to Manager"));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> handleLogout());

        panel.add(viewLogButton);
        panel.add(allocateAssetButton);
        panel.add(configureRateButton);
        panel.add(manageInsuranceButton);
        panel.add(assignDoctorButton);
        panel.add(logoutButton);

        return panel;
    }

    /**
     * Shows the raw audit log lines for this Admin. Basic display for now —
     * formatting into a proper table is a good follow-up improvement.
     */
    private void showActivityLog() {
        List<String> logs = loggedInAdmin.viewActivityLog();

        JTextArea textArea = new JTextArea(15, 50);
        textArea.setEditable(false);

        if (logs.isEmpty()) {
            textArea.setText("No activity logged yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String line : logs) {
                sb.append(line).append("\n");
            }
            textArea.setText(sb.toString());
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Activity Log", JOptionPane.PLAIN_MESSAGE);
    }

    private void notImplementedYet(String featureName) {
        JOptionPane.showMessageDialog(this,
                "\"" + featureName + "\" isn't built yet — coming soon.",
                "Not Implemented", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleLogout() {
        this.dispose();
        SwingUtilities.invokeLater(() -> new controller.LoginScreen().setVisible(true));
    }
}
 