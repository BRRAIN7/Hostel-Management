///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
// */
//package UserInterface;
//
//import Entities.StudentComplaint;
//import Services.ComplaintController;
//import Services.Session;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.util.List;
//
///**
// *
// * @author karole
// */
//public class FileComplaintUI extends javax.swing.JFrame {
//
//    /**
//     * Creates new form FileComplaintUI
//     */
//    private String studentId = Session.getUserId();
//    public FileComplaintUI() {
//        initComponents();
//        loadComplaints();
//    }
//    private void loadComplaints() {
//        try {
//            ComplaintController controller = new ComplaintController();
//            List<StudentComplaint> complaints = controller.getComplaintsByStudent(studentId);
//
//            DefaultTableModel model = new DefaultTableModel();
//            model.setColumnIdentifiers(new String[]{"Complaint ID", "Description", "Status", "Resolution"});
//
//            for (StudentComplaint c : complaints) {
//                model.addRow(new Object[]{
//                        c.getComplaintId(),
//                        c.getDescription(),
//                        c.getStatus(),
//                        c.getResolution() != null ? c.getResolution() : "N/A"
//                });
//            }
//
//            jTable1.setModel(model);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Failed to load complaints.");
//        }
//    }
//    /**
//     * This method is called from within the constructor to initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is always
//     * regenerated by the Form Editor.
//     */
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        jScrollPane1 = new javax.swing.JScrollPane();
//        jTable1 = new javax.swing.JTable();
//        jSeparator1 = new javax.swing.JSeparator();
//        jLabel1 = new javax.swing.JLabel();
//        jLabel2 = new javax.swing.JLabel();
//        jLabel3 = new javax.swing.JLabel();
//        jTextField1 = new javax.swing.JTextField();
//        jButton1 = new javax.swing.JButton();
//        jButton2 = new javax.swing.JButton();
//
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//
//        jTable1.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] {
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null}
//            },
//            new String [] {
//                "Title 1", "Title 2", "Title 3", "Title 4"
//            }
//        ));
//        jScrollPane1.setViewportView(jTable1);
//
//        jLabel1.setText("File Complaint");
//
//        jLabel2.setText("Previous Complaints");
//
//        jLabel3.setText("Enter Description of you complaint ");
//
//        jTextField1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jTextField1ActionPerformed(evt);
//            }
//        });
//
//        jButton1.setText("File Complaint");
//        jButton1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton1ActionPerformed(evt);
//            }
//        });
//
//        jButton2.setText("Refresh");
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(42, 42, 42))
//            .addGroup(layout.createSequentialGroup()
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(layout.createSequentialGroup()
//                        .addGap(49, 49, 49)
//                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addGroup(layout.createSequentialGroup()
//                        .addGap(436, 436, 436)
//                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addGroup(layout.createSequentialGroup()
//                        .addGap(20, 20, 20)
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 883, javax.swing.GroupLayout.PREFERRED_SIZE)
//                            .addGroup(layout.createSequentialGroup()
//                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                                    .addComponent(jButton1)
//                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                    .addGroup(layout.createSequentialGroup()
//                                        .addGap(18, 18, 18)
//                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                    .addGroup(layout.createSequentialGroup()
//                                        .addGap(149, 149, 149)
//                                        .addComponent(jButton2)))))))
//                .addContainerGap(19, Short.MAX_VALUE))
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(jLabel1)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(layout.createSequentialGroup()
//                        .addGap(74, 74, 74)
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
//                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 243, Short.MAX_VALUE)
//                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                        .addComponent(jLabel2)
//                        .addGap(50, 50, 50)
//                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addGroup(layout.createSequentialGroup()
//                        .addGap(232, 232, 232)
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                            .addComponent(jButton2)
//                            .addComponent(jButton1))
//                        .addGap(0, 0, Short.MAX_VALUE))))
//        );
//
//        pack();
//    }// </editor-fold>//GEN-END:initComponents
//
//    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
//        // TODO add your handling code here:
//    }//GEN-LAST:event_jTextField1ActionPerformed
//
//    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        String description = jTextField1.getText().trim();
//        if (description.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please enter a description.");
//            return;
//        }
//
//        ComplaintController controller = new ComplaintController();
//        String result = controller.fileComplaint(studentId, description);
//        JOptionPane.showMessageDialog(this, result);
//
//        jTextField1.setText("");
//        loadComplaints();
//    }//GEN-LAST:event_jButton1ActionPerformed
//
//
//    public static void openFileNewComplaint() {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FileComplaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FileComplaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FileComplaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FileComplaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FileComplaintUI().setVisible(true);
//            }
//        });
//    }
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JButton jButton1;
//    private javax.swing.JButton jButton2;
//    private javax.swing.JLabel jLabel1;
//    private javax.swing.JLabel jLabel2;
//    private javax.swing.JLabel jLabel3;
//    private javax.swing.JScrollPane jScrollPane1;
//    private javax.swing.JSeparator jSeparator1;
//    private javax.swing.JTable jTable1;
//    private javax.swing.JTextField jTextField1;
//    // End of variables declaration//GEN-END:variables
//}



package UserInterface;
import Entities.StudentComplaint;
import Services.ComplaintController;
import Services.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

public class FileComplaintUI extends JFrame {
    private BufferedImage bgImage;
    private JTextArea complaintTextArea;
    private JTable previousComplaintsTable;
    private DefaultTableModel tableModel;


    public FileComplaintUI() {
        setTitle("Complaint Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1300, 700);
        setLocationRelativeTo(null); // Center on screen

        // Load background image with error handling
        try {
            URL imageUrl = getClass().getResource("/UserInterface/h6.jpg");
            if (imageUrl != null) {
                bgImage = ImageIO.read(imageUrl);
            } else {
                throw new FileNotFoundException("Image resource not found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Background image could not be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            logErrorToFile(e);
        }

        // Set custom panel with background
        setContentPane(new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        });

        setLayout(null);

        JLabel titleLabel = new JLabel("File Complaint");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(30, 10, 300, 40);
        add(titleLabel);

        JLabel descLabel = new JLabel("Enter Description of your complaint");
        descLabel.setBounds(100, 80, 250, 20);
        add(descLabel);

        complaintTextArea = new JTextArea();
        complaintTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(complaintTextArea);
        scrollPane.setBounds(350, 70, 300, 80);
        add(scrollPane);

        JButton fileBtn = new JButton("File Complaint");
        fileBtn.setBounds(280, 180, 130, 30);
        add(fileBtn);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(450, 180, 100, 30);
        add(refreshBtn);

        JSeparator separator = new JSeparator();
        separator.setBounds(20, 230, 850, 10);
        add(separator);

        JLabel previousLabel = new JLabel("Previous Complaints");
        previousLabel.setFont(new Font("Arial", Font.BOLD, 16));
        previousLabel.setBounds(370, 240, 200, 30);
        add(previousLabel);

        // Table for previous complaints
        String[] columns = { "Complaint ID", "Description", "Status", "Resolution" };
        Object[][] data = {
                { null, null, null, null },
                { null, null, null, null },
        };

        tableModel = new DefaultTableModel(data, columns);
        previousComplaintsTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(previousComplaintsTable);
        tableScroll.setBounds(50, 280, 800, 200);
        add(tableScroll);
        loadPreviousComplaints();
        // Add button actions
        fileBtn.addActionListener(e -> handleFileComplaint());
        refreshBtn.addActionListener(e -> handleRefresh());

        setVisible(true);
    }
    private void loadPreviousComplaints() {
        try {
            String studentId = Session.getUserId(); // Same as original
            ComplaintController controller = new ComplaintController();

            List<StudentComplaint> complaints = controller.getComplaintsByStudent(studentId);

            // Clear the existing table data
            tableModel.setRowCount(0);

            // Populate new data
            for (StudentComplaint c : complaints) {
                tableModel.addRow(new Object[]{
                        c.getComplaintId(),
                        c.getDescription(),
                        c.getStatus(),
                        c.getResolution() != null ? c.getResolution() : "N/A"
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load complaints: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleFileComplaint() {
        String description = complaintTextArea.getText().trim();

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a description.");
            return;
        }

        try {
            String studentId = Session.getUserId(); // Use session-based ID
            ComplaintController controller = new ComplaintController();

            String result = controller.fileComplaint(studentId, description);
            JOptionPane.showMessageDialog(this, result);

            complaintTextArea.setText(""); // Clear the input
            loadPreviousComplaints();      // Reload updated table
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleRefresh() {
        // Placeholder: Just shows refresh action
        JOptionPane.showMessageDialog(this, "Refreshed complaints list.", "Refreshed", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logErrorToFile(Exception e) {
        try (FileWriter fw = new FileWriter("error_log.txt", true)) {
            fw.write("[" + new java.util.Date() + "] " + e.toString() + "\n");
        } catch (IOException ioException) {
            System.err.println("Could not write to log file: " + ioException);
        }
    }

    public void openFileComplaint() {
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }
}