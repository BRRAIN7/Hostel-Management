package UserInterface;

import Services.PaymentService;
import Services.Session;
import Validation.PaymentValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
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

        initComponents();
        setupUI();
    }

    private void initComponents() {
        setTitle("Fee Payment System");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        pendingAmountLabel = new JLabel("Loading pending amount...");
        amountTextField = new JTextField(10);
        passwordField = new JPasswordField(10);
        paymentMethodCombo = new JComboBox<>(new String[]{"Credit Card", "Bank Transfer", "Cash"});
        payButton = new JButton("Pay Fees");
        resetButton = new JButton("Reset");

        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Pending Amount:"));
        panel1.add(pendingAmountLabel);

        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Amount to Pay:"));
        panel2.add(amountTextField);

        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("Payment Method:"));
        panel3.add(paymentMethodCombo);

        JPanel panel4 = new JPanel();
        panel4.add(new JLabel("Password:"));
        panel4.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(payButton);
        buttonPanel.add(resetButton);

        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);
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
}