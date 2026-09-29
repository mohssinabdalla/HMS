package controller.MedicalManager;

import model.MedicalManager;
import javax.swing.JOptionPane;
import exceptions.ReportExceptions.ReportGenerationException;

public class MedicalManagerInterface extends javax.swing.JFrame {
    
    private MedicalManager currentManager;    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MedicalManagerInterface.class.getName());

    //Default constructor
    public MedicalManagerInterface() {
       initComponents();
       setLocationRelativeTo(null);
       updateWelcomeMessage();
    }

    //constructor used after login
    public MedicalManagerInterface(MedicalManager manager) {
        this.currentManager = manager;
        initComponents();
        setLocationRelativeTo(null); // Centers window on screen
        updateWelcomeMessage();
    }
    
    private void updateWelcomeMessage() {
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        if (currentManager != null) {
            lblWelcome.setText("Welcome, " + currentManager.getFullName());
        } else {
            lblWelcome.setText("Welcome, Medical Manager");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnManageDepartments = new javax.swing.JButton();
        btnManageRosters = new javax.swing.JButton();
        btnViewReports = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnProfile = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnManageDepartments.setText("Department Management");
        btnManageDepartments.addActionListener(this::btnManageDepartmentsActionPerformed);

        btnManageRosters.setText("Shift Roster Coordination");
        btnManageRosters.addActionListener(this::btnManageRostersActionPerformed);

        btnViewReports.setText("Hospital Performance Reports");
        btnViewReports.addActionListener(this::btnViewReportsActionPerformed);

        btnLogout.setText("Log Out");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        btnProfile.setText("Edit Profile");
        btnProfile.addActionListener(this::btnProfileActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnManageRosters, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnViewReports, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnManageDepartments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnManageDepartments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnManageRosters)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnViewReports)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 103, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Medical Manager Dashboard");

        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(86, 86, 86)
                            .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnManageDepartmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageDepartmentsActionPerformed
         openDepartmentDialog();
    }//GEN-LAST:event_btnManageDepartmentsActionPerformed

    private void btnViewReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewReportsActionPerformed
        openReportsDialog();
    }//GEN-LAST:event_btnViewReportsActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        handleLogout();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnManageRostersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageRostersActionPerformed
        openShiftRosterDialog();
    }//GEN-LAST:event_btnManageRostersActionPerformed

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        openEditProfileDialog();
    }//GEN-LAST:event_btnProfileActionPerformed

    private void openEditProfileDialog() {
        MMProfileDialog profileDialog = new MMProfileDialog(this, true, currentManager);
        profileDialog.setLocationRelativeTo(this);
        profileDialog.setVisible(true);
        updateWelcomeMessage();
    }

    private void openDepartmentDialog() {
        DepartmentDialog deptDialog = new DepartmentDialog(this, true);
        deptDialog.setLocationRelativeTo(this);
        deptDialog.setVisible(true);
    }

    private void openShiftRosterDialog() {
        ShiftRosterDialog rosterDialog = new ShiftRosterDialog(this, true);
        rosterDialog.setLocationRelativeTo(this);
        rosterDialog.setVisible(true);
    }
    private void openReportsDialog() {
        String[] options = {"Hospital Metrics Report", "Revenue Summary Report"};
        int choice = JOptionPane.showOptionDialog(
            this, 
            "Select the report you want to view:", 
            "Medical Reports",
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]
        );

        if (choice == 0) {
            showMetricsReport();
        } else if (choice == 1) {
            showRevenueReport();
        }
    }

    private void showMetricsReport() {
        try {
            String reportContent = generateMetricsReportData();
            JOptionPane.showMessageDialog(
                this,
                reportContent,
                "Hospital Metrics Report",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (ReportGenerationException e) {
            JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Report Generation Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private String generateMetricsReportData() throws ReportGenerationException {
        if (currentManager == null) {
            currentManager = new model.MedicalManager("M001", "manager1", "pass", "Dr. Manager", "manager@hospital.com", "0123456789");
        }

        return """
               === Hospital Metrics Report ===
               Report ID: REP-M01
               Generated Date: 2026-09-26

               • Total Patient Admissions: %d
               • Occupancy Rate: %.1f%%
               • Active Shift Count: %d
               """.formatted(
                   currentManager.getTotalPatientAdmissions(),
                   currentManager.getOccupancyRate(),
                   currentManager.getActiveShiftCount()
               );
    }

    private void showRevenueReport() {
        try {
            String reportContent = generateRevenueReportData();
            JOptionPane.showMessageDialog(
                this,
                reportContent,
                "Revenue Summary Report",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (ReportGenerationException e) {
            JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Report Generation Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String generateRevenueReportData() throws ReportGenerationException {
        if (currentManager == null) {
            currentManager = new model.MedicalManager("M001", "manager1", "pass", "Dr. Manager", "manager@hospital.com", "0123456789");
        }

        return """
               === Revenue Summary Report ===
               Report ID: REP-R01
               Generated Date: 2026-09-26

               • Total Consultation Fees: $%.2f
               • Insurance Claims: $%.2f
               • Monthly Revenue: $%.2f
               """.formatted(
                   currentManager.getTotalConsultationFees(),
                   currentManager.getInsuranceClaims(),
                   currentManager.getMonthlyRevenue()
               );
    }

    private void handleLogout() {
        JOptionPane.showMessageDialog(
            this, 
            "Logged out successfully.", 
            "Logout", 
            JOptionPane.INFORMATION_MESSAGE
        );

        // Return to the login screen
        new controller.LoginScreen().setVisible(true); // Adjust package name if LoginScreen is in a different package
        this.dispose();
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(() -> new MedicalManagerInterface().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnManageDepartments;
    private javax.swing.JButton btnManageRosters;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnViewReports;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblWelcome;
    // End of variables declaration//GEN-END:variables
}
