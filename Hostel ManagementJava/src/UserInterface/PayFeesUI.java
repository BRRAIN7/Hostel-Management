package UserInterface;

import Services.PaymentService;
import Services.Session;
import Validation.PaymentValidator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;

public class PayFeesUI extends JFrame {
    // UI Components
    private JLabel pendingAmountLabel;
    private JTextField amountTextField;
    private JPasswordField passwordField;
    private JComboBox<String> paymentMethodCombo;
    private JButton payButton;
    private JButton resetButton;

    // Services and validation
    private final PaymentService paymentService;
    private final PaymentValidator validator;

    public PayFeesUI() {
        this.paymentService = new PaymentService();
        this.validator = new PaymentValidator();

        try {
            ImagePanel backgroundPanel = new ImagePanel("src/UserInterface/h6.jpg"); // Adjust path if needed
            backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
            backgroundPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            setContentPane(backgroundPanel);
        } catch (Exception e) {
            // Fallback if image fails
            JPanel fallback = new JPanel();
            fallback.setLayout(new BoxLayout(fallback, BoxLayout.Y_AXIS));
            fallback.setBackground(new Color(230, 230, 230));
            fallback.setBorder(new EmptyBorder(20, 20, 20, 20));
            setContentPane(fallback);

            JOptionPane.showMessageDialog(this,
                    "Failed to load background image: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        initComponents();
        setupUI();
    }

    private void initComponents() {
        setTitle("Fee Payment System");
        setSize(1300, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        pendingAmountLabel = new JLabel("Loading pending amount...");
        amountTextField = new JTextField(10);
        passwordField = new JPasswordField(10);
        paymentMethodCombo = new JComboBox<>(new String[]{"Credit Card", "Bank Transfer", "Cash"});
        payButton = new JButton("Pay Fees");
        resetButton = new JButton("Reset");

        // Set text color to black
        pendingAmountLabel.setForeground(Color.BLACK);
        amountTextField.setForeground(Color.BLACK);
        passwordField.setForeground(Color.BLACK);
        payButton.setForeground(Color.WHITE);
        resetButton.setForeground(Color.WHITE);

        // Apply rounded corners and customize text fields and buttons
        amountTextField.setBorder(new LineBorder(Color.GRAY, 1, true));
        passwordField.setBorder(new LineBorder(Color.GRAY, 1, true));
        payButton.setBorder(new LineBorder(Color.GRAY, 2, true));
        resetButton.setBorder(new LineBorder(Color.GRAY, 2, true));

        // Set background colors and fonts
        amountTextField.setBackground(new Color(240, 240, 240));
        passwordField.setBackground(new Color(240, 240, 240));
        payButton.setBackground(new Color(56, 142, 60)); // Green button
        resetButton.setBackground(new Color(244, 67, 54)); // Red button

        payButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        amountTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add hover effect for buttons
        payButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                payButton.setBackground(new Color(46, 125, 50)); // Darker green on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                payButton.setBackground(new Color(56, 142, 60)); // Original green
            }
        });

        resetButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                resetButton.setBackground(new Color(229, 57, 53)); // Darker red on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                resetButton.setBackground(new Color(244, 67, 54)); // Original red
            }
        });

        // Panels with transparency
        JPanel panel1 = new JPanel();
        panel1.setOpaque(false);
        JLabel label1 = new JLabel("Pending Amount:");
        label1.setForeground(Color.BLACK);
        panel1.add(label1);
        panel1.add(pendingAmountLabel);

        JPanel panel2 = new JPanel();
        panel2.setOpaque(false);
        JLabel label2 = new JLabel("Amount to Pay:");
        label2.setForeground(Color.BLACK);
        panel2.add(label2);
        panel2.add(amountTextField);

        JPanel panel3 = new JPanel();
        panel3.setOpaque(false);
        JLabel label3 = new JLabel("Payment Method:");
        label3.setForeground(Color.BLACK);
        panel3.add(label3);
        panel3.add(paymentMethodCombo);

        JPanel panel4 = new JPanel();
        panel4.setOpaque(false);
        JLabel label4 = new JLabel("Password:");
        label4.setForeground(Color.BLACK);
        panel4.add(label4);
        panel4.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(payButton);
        buttonPanel.add(resetButton);

        add(panel1);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(panel2);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(panel3);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(panel4);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(buttonPanel);

        payButton.addActionListener(this::handlePayment);
        resetButton.addActionListener(e -> resetFields());
    }

    private void setupUI() {
        refreshPendingAmount();
    }

    private void refreshPendingAmount() {
        String studentId = Session.getUserId();
        try {
            double totalPending = paymentService.calculatePendingAmount(studentId);
            pendingAmountLabel.setText(String.format("₹%.2f", totalPending));
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error loading pending amount: " + ex.getMessage());
        }
    }

    private void handlePayment(ActionEvent evt) {
        String studentId = Session.getUserId();
        String password = new String(passwordField.getPassword());

        if (!validator.isValidPassword(password, Session.getUserPassword())) {
            JOptionPane.showMessageDialog(this, "Invalid password!");
            return;
        }

        try {
            double paymentAmount = Double.parseDouble(amountTextField.getText());
            String paymentMethod = (String) paymentMethodCombo.getSelectedItem();

            paymentService.processPayment(studentId, paymentAmount, paymentMethod);

            JOptionPane.showMessageDialog(this, "Payment successful!");
            refreshPendingAmount();
            resetFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount format!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Payment failed: " + ex.getMessage());
        }
    }

    private void resetFields() {
        amountTextField.setText("");
        passwordField.setText("");
        paymentMethodCombo.setSelectedIndex(0);
    }

    // Custom JPanel class that paints a background image
    class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) throws Exception {
            File file = new File(imagePath);
            if (!file.exists()) {
                throw new Exception("Background image not found: " + imagePath);
            }

            backgroundImage = new ImageIcon(imagePath).getImage();
            if (backgroundImage.getWidth(null) <= 0 || backgroundImage.getHeight(null) <= 0) {
                throw new Exception("Invalid or corrupted image file.");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}







//package UserInterface;
//
//import Services.PaymentService;
//import Services.Session;
//import Validation.PaymentValidator;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.SQLException;
//
//public class PayFeesUI extends JFrame {
//    private JLabel pendingLabel;
//    private JTextField amountField;
//    private JComboBox<String> paymentMethodBox;
//    private JPasswordField passwordField;
//    private JButton payButton, resetButton;
//
//    private final PaymentService paymentService;
//    private final PaymentValidator validator;
//
//    public PayFeesUI() {
//        // Initialize services and validators
//        this.paymentService = new PaymentService();
//        this.validator = new PaymentValidator();
//
//        setTitle("Fee Payment System");
//        setSize(1300, 700);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        // Load and set background image
//        ImageIcon backgroundIcon;
//        try {
//            backgroundIcon = new ImageIcon("src/UI/h6.jpg");
//            Image image = backgroundIcon.getImage();
//            Image scaled = image.getScaledInstance(1300, 700, Image.SCALE_SMOOTH);
//            backgroundIcon = new ImageIcon(scaled);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Background image not found. Using default background.");
//            getContentPane().setBackground(new Color(220, 225, 230));
//            setLayout(null);
//            initUIComponents();
//            setVisible(true);
//            return;
//        }
//
//        JLabel backgroundLabel = new JLabel(backgroundIcon);
//        backgroundLabel.setLayout(null);
//        setContentPane(backgroundLabel);
//
//        initUIComponents();
//        setVisible(true);
//    }
//
//    private void initUIComponents() {
//        // Panel to hold all fields centered
//        JPanel panel = new JPanel(null);
//        panel.setBounds(450, 100, 400, 300);
//        panel.setOpaque(false); // transparent panel over background
//        add(panel);
//
//        // Pending Amount
//        pendingLabel = new JLabel("Pending Amount: ₹0.00", JLabel.CENTER);
//        pendingLabel.setBounds(75, 0, 250, 25);
//        pendingLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        panel.add(pendingLabel);
//
//        // Amount to Pay
//        JLabel amountLabel = new JLabel("Amount to Pay:");
//        amountLabel.setBounds(30, 50, 120, 25);
//        amountLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        panel.add(amountLabel);
//
//        amountField = new JTextField();
//        amountField.setBounds(160, 50, 180, 25);
//        amountField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//        panel.add(amountField);
//
//        // Payment Method
//        JLabel methodLabel = new JLabel("Payment Method:");
//        methodLabel.setBounds(30, 95, 120, 25);
//        methodLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        panel.add(methodLabel);
//
//        String[] methods = {"Credit Card", "Debit Card", "UPI", "Net Banking"};
//        paymentMethodBox = new JComboBox<>(methods);
//        paymentMethodBox.setBounds(160, 95, 180, 25);
//        panel.add(paymentMethodBox);
//
//        // Password
//        JLabel passwordLabel = new JLabel("Password:");
//        passwordLabel.setBounds(30, 140, 120, 25);
//        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        panel.add(passwordLabel);
//
//        passwordField = new JPasswordField();
//        passwordField.setBounds(160, 140, 180, 25);
//        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//        panel.add(passwordField);
//
//        // Pay Button
//        payButton = new JButton("Pay Fees");
//        payButton.setBounds(80, 200, 100, 30);
//        payButton.setBackground(new Color(0, 102, 204));
//        payButton.setForeground(Color.BLACK);
//        payButton.setFocusPainted(false);
//        panel.add(payButton);
//
//        // Reset Button
//        resetButton = new JButton("Reset");
//        resetButton.setBounds(210, 200, 100, 30);
//        resetButton.setBackground(Color.GRAY);
//        resetButton.setForeground(Color.black);
//        resetButton.setFocusPainted(false);
//        panel.add(resetButton);
//
//        // Action Listeners
//        payButton.addActionListener(e -> processPayment());
//        resetButton.addActionListener(e -> resetFields());
//    }
//
//    private void processPayment() {
//        String amountText = amountField.getText().trim();
//        String password = new String(passwordField.getPassword()).trim();
//
//        // Validate Amount
//        if (amountText.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please enter an amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        try {
//            double amount = Double.parseDouble(amountText);
//            if (amount <= 0) {
//                JOptionPane.showMessageDialog(this, "Please enter a valid amount greater than 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Amount must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Validate Password
//        if (password.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please enter your password.", "Input Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (!validator.isValidPassword(password, Session.getUserPassword())) {
//            JOptionPane.showMessageDialog(this, "Invalid password!");
//            return;
//        }
//
//        // Get Payment Method
//        String method = (String) paymentMethodBox.getSelectedItem();
//
//        // Process Payment
//        try {
//            String studentId = Session.getUserId();
//            double amount = Double.parseDouble(amountText);
//            paymentService.processPayment(studentId, amount, method);
//
//            JOptionPane.showMessageDialog(this, "₹" + amountText + " paid via " + method + ".", "Payment Success", JOptionPane.INFORMATION_MESSAGE);
//            refreshPendingAmount();
//            resetFields();
//        } catch (SQLException | ClassNotFoundException e) {
//            JOptionPane.showMessageDialog(this, "Payment failed: " + e.getMessage(), "Payment Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void resetFields() {
//        amountField.setText("");
//        passwordField.setText("");
//        paymentMethodBox.setSelectedIndex(0);
//    }
//
//    private void refreshPendingAmount() {
//        // Retrieve pending amount and update the label
//        String studentId = Session.getUserId();
//        try {
//            double totalPending = paymentService.calculatePendingAmount(studentId);
//            pendingLabel.setText(String.format("Pending Amount: ₹%.2f", totalPending));
//        } catch (SQLException | ClassNotFoundException e) {
//            JOptionPane.showMessageDialog(this, "Error loading pending amount: " + e.getMessage());
//        }
//    }
//
//    public void openpayfees() {
//        SwingUtilities.invokeLater(() -> this.setVisible(true));
//    }
//}
