package controller.MedicalManager;

import exceptions.RosterExceptions.ShiftConflictException;
import exceptions.RosterExceptions.InvalidShiftTimeException;
import javax.swing.JOptionPane;

public class ShiftRosterDialog extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ShiftRosterDialog.class.getName());    
    private static final java.util.Set<String> assignedShifts = new java.util.HashSet<>();
    
    public ShiftRosterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDoctorInfo = new javax.swing.JTextField();
        txtShiftDate = new javax.swing.JTextField();
        cmbTiming = new javax.swing.JComboBox<>();
        cmbDepartment = new javax.swing.JComboBox<>();
        btnAssignShift = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(41, 128, 185));
        jPanel1.setPreferredSize(new java.awt.Dimension(450, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Create Doctor Shift Roster");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(207, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(192, 192, 192))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(16, 16, 16))
        );

        jLabel2.setText("Doctor ID / Name:");

        jLabel3.setText("Shift Date (YYYY-MM-DD):");

        jLabel4.setText("Shift Timing:");

        jLabel5.setText("Department:");

        txtShiftDate.setText("2026-09-26");

        cmbTiming.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Morning (08:00 - 16:00)", "Evening (16:00 - 00:00)", "Night (00:00 - 08:00)" }));
        cmbTiming.addItemListener(this::cmbTimingItemStateChanged);

        cmbDepartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Emergency", "Cardiology", "Pediatrics", "Orthopedics" }));

        btnAssignShift.setBackground(new java.awt.Color(46, 204, 113));
        btnAssignShift.setForeground(new java.awt.Color(255, 255, 255));
        btnAssignShift.setText("Assign Shift");
        btnAssignShift.setFocusPainted(false);
        btnAssignShift.addActionListener(this::btnAssignShiftActionPerformed);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(104, 104, 104)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDoctorInfo)
                            .addComponent(txtShiftDate)
                            .addComponent(cmbTiming, 0, 191, Short.MAX_VALUE)
                            .addComponent(cmbDepartment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAssignShift)
                        .addGap(44, 44, 44)
                        .addComponent(btnCancel)
                        .addGap(121, 121, 121))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDoctorInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtShiftDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbTiming, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAssignShift)
                    .addComponent(btnCancel))
                .addGap(47, 47, 47))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTimingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTimingItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTimingItemStateChanged

    private void btnAssignShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignShiftActionPerformed
        saveShiftRoster(); // Delegate to external method
    }//GEN-LAST:event_btnAssignShiftActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose(); // Close dialog
    }//GEN-LAST:event_btnCancelActionPerformed

    private void saveShiftRoster() {
        String docInfo = txtDoctorInfo.getText().trim();
        String shiftDate = txtShiftDate.getText().trim();
        String timing = cmbTiming.getSelectedItem().toString();
        String dept = cmbDepartment.getSelectedItem().toString();

        // 1. Basic validation for empty fields
        if (docInfo.isEmpty() || shiftDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all required fields.", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 2. Validate date format (YYYY-MM-DD)
            if (!shiftDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new InvalidShiftTimeException("Invalid date format. Please use YYYY-MM-DD.");
            }

            // 3. Create a unique identifier key for this shift booking
            String shiftKey = docInfo.toLowerCase() + "|" + shiftDate + "|" + timing;

            // 4. Check if doctor is already assigned to this shift date & time
            if (assignedShifts.contains(shiftKey)) {
                throw new ShiftConflictException("Shift Conflict: Doctor '" + docInfo + "' is already assigned to a shift on " + shiftDate + " (" + timing + ").");
            }

            // 5. Store shift in memory upon successful validation
            assignedShifts.add(shiftKey);

            String message = """
                    Shift Roster Assigned Successfully!

                    Doctor: %s
                    Date: %s
                    Timing: %s
                    Department: %s""".formatted(docInfo, shiftDate, timing, dept);

            JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

        } catch (ShiftConflictException ex) {
            // Triggers when assigning the same shift twice
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Shift Conflict Error", 
                JOptionPane.ERROR_MESSAGE);

        } catch (InvalidShiftTimeException ex) {
            // Triggers on malformed date string
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Invalid Shift Time", 
                JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "An unexpected error occurred: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ShiftRosterDialog dialog = new ShiftRosterDialog(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    public static int getActiveShiftCount() {
    return assignedShifts.size();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAssignShift;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JComboBox<String> cmbTiming;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtDoctorInfo;
    private javax.swing.JTextField txtShiftDate;
    // End of variables declaration//GEN-END:variables
}
