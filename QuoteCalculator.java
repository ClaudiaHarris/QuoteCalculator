import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class QuoteCalculator extends JFrame {
    private JSpinner ageSpinner, drivingYearsSpinner;
    private JCheckBox accidentCheckBox;
    private JLabel basePremiumLabel, fullPaymentLabel, downPaymentLabel, remainingLabel, monthlyLabel, agentLabel;
    private JButton contactAgentButton, emailQuoteButton;

    public QuoteCalculator() {
        setTitle("Insurance Quote Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        // Input Panel (NORTH)
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(220, 220, 220));
        JLabel titleLabel = new JLabel("Enter Your Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(titleLabel);
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("Age:"));
        ageSpinner = new JSpinner(new SpinnerNumberModel(18, 16, 120, 1));
        ageSpinner.setToolTipText("Your age (16-120)");
        inputPanel.add(ageSpinner);

        inputPanel.add(new JLabel("Years Driving:"));
        drivingYearsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        drivingYearsSpinner.setToolTipText("Years of driving experience (0-100)");
        inputPanel.add(drivingYearsSpinner);

        inputPanel.add(new JLabel("Accidents?"));
        accidentCheckBox = new JCheckBox();
        accidentCheckBox.setToolTipText("Check if you’ve had accidents in the last 5 years");
        inputPanel.add(accidentCheckBox);
        add(inputPanel, BorderLayout.NORTH);

        // Output Panel (CENTER) - Constrain height
        JPanel outputPanel = new JPanel(new GridLayout(5, 1, 0, 10));
        outputPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        outputPanel.setBackground(new Color(240, 240, 240));
        outputPanel.setPreferredSize(new Dimension(350, 150)); // Fixed height for 5 labels
        basePremiumLabel = new JLabel("Total Premium: $0.00");
        fullPaymentLabel = new JLabel("Full Payment (5% off): $0.00");
        downPaymentLabel = new JLabel("Down Payment (10%): $0.00");
        remainingLabel = new JLabel("Remaining Balance: $0.00");
        monthlyLabel = new JLabel("6 Monthly Payments: $0.00");
        JLabel[] labels = {basePremiumLabel, fullPaymentLabel, downPaymentLabel, remainingLabel, monthlyLabel};
        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 13));
            label.setHorizontalAlignment(SwingConstants.LEFT);
            outputPanel.add(label);
        }
        add(outputPanel, BorderLayout.CENTER);

        // Button Panel (SOUTH)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        buttonPanel.setBackground(new Color(220, 220, 220));
        JButton calculateButton = new JButton("Calculate Quote");
        calculateButton.setPreferredSize(new Dimension(120, 30));
        calculateButton.setToolTipText("Calculate your insurance quote");

        contactAgentButton = new JButton("Contact Agent");
        contactAgentButton.setPreferredSize(new Dimension(120, 30));
        contactAgentButton.setToolTipText("Request agent contact after viewing quote");
        contactAgentButton.setEnabled(false);

        emailQuoteButton = new JButton("Email Quote");
        emailQuoteButton.setPreferredSize(new Dimension(120, 30));
        emailQuoteButton.setToolTipText("Email your quote to yourself");
        emailQuoteButton.setEnabled(false);

        agentLabel = new JLabel("");
        agentLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        buttonPanel.add(calculateButton);
        buttonPanel.add(contactAgentButton);
        buttonPanel.add(emailQuoteButton);
        buttonPanel.add(agentLabel);
        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        calculateButton.addActionListener(e -> calculateAndDisplay());
        contactAgentButton.addActionListener(e -> showAgentPrompt());
        emailQuoteButton.addActionListener(e -> sendQuoteEmail());

        pack(); // Auto-size window based on preferred sizes
        setMinimumSize(new Dimension(400, 400)); // Prevent collapse
    }

    private void calculateAndDisplay() {
        try {
            int age = (Integer) ageSpinner.getValue();
            int drivingYears = (Integer) drivingYearsSpinner.getValue();
            if (age < 0 || drivingYears < 0) throw new NumberFormatException();
            boolean hasAccidents = accidentCheckBox.isSelected();

            PremiumDetails details = calculatePremium(age, drivingYears, hasAccidents);
            basePremiumLabel.setText(String.format("Total Premium: $%.2f", details.basePremium));
            fullPaymentLabel.setText(String.format("Full Payment (5%% off): $%.2f", details.fullPaymentDiscount));
            downPaymentLabel.setText(String.format("Down Payment (10%%): $%.2f", details.downPayment));
            remainingLabel.setText(String.format("Remaining Balance: $%.2f", details.remainingBalance));
            monthlyLabel.setText(String.format("6 Monthly Payments: $%.2f", details.monthlyPayment));

            agentLabel.setText("");
            contactAgentButton.setEnabled(true);
            emailQuoteButton.setEnabled(true);
        } catch (NumberFormatException ex) {
            agentLabel.setText("Error: Enter valid positive numbers.");
            contactAgentButton.setEnabled(false);
            emailQuoteButton.setEnabled(false);
        }
    }

    private void showAgentPrompt() {
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JTextField nameField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        String[] contactMethods = {"Phone", "Email"};
        JComboBox<String> contactMethodBox = new JComboBox<>(contactMethods);

        infoPanel.add(new JLabel("Name:"));
        infoPanel.add(nameField);
        infoPanel.add(new JLabel("Phone:"));
        infoPanel.add(phoneField);
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(emailField);
        infoPanel.add(new JLabel("Preferred Contact:"));
        infoPanel.add(contactMethodBox);

        while (true) {
            int result = JOptionPane.showConfirmDialog(this, infoPanel, "Enter Contact Details", JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this, "Agent request canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                contactAgentButton.setEnabled(false);
                emailQuoteButton.setEnabled(true);
                return;
            }

            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String contactMethod = (String) contactMethodBox.getSelectedItem();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Name is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if (phone.isEmpty() && email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: At least one contact method (phone or email) is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if (!phone.isEmpty() && !phone.matches("\\d{3}-\\d{3}-\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Error: Phone must be in XXX-XXX-XXXX format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if (!email.isEmpty() && !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                JOptionPane.showMessageDialog(this, "Error: Invalid email format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            String contactInfo = phone.isEmpty() ? email : phone;
            String message = "Agent will contact " + name + " via " + contactMethod.toLowerCase() + " at " + contactInfo;
            JOptionPane.showMessageDialog(this, message, "Request Submitted", JOptionPane.INFORMATION_MESSAGE);
            contactAgentButton.setEnabled(false);
            emailQuoteButton.setEnabled(true);
            break;
        }
    }

    private void sendQuoteEmail() {
        JPanel emailPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        emailPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JTextField emailField = new JTextField(15);
        emailPanel.add(new JLabel("Email:"));
        emailPanel.add(emailField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(this, emailPanel, "Enter Email for Quote", JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this, "Email request canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                contactAgentButton.setEnabled(true);
                emailQuoteButton.setEnabled(false);
                return;
            }

            String email = emailField.getText().trim();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Email is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                JOptionPane.showMessageDialog(this, "Error: Invalid email format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            String quote = String.format(
                "Your Insurance Quote:\n" +
                "Total Premium: $%.2f\n" +
                "Full Payment (5%% off): $%.2f\n" +
                "Down Payment (10%%): $%.2f\n" +
                "Remaining Balance: $%.2f\n" +
                "6 Monthly Payments: $%.2f each",
                Double.parseDouble(basePremiumLabel.getText().split("\\$")[1]),
                Double.parseDouble(fullPaymentLabel.getText().split("\\$")[1]),
                Double.parseDouble(downPaymentLabel.getText().split("\\$")[1]),
                Double.parseDouble(remainingLabel.getText().split("\\$")[1]),
                Double.parseDouble(monthlyLabel.getText().split("\\$")[1])
            );
            JOptionPane.showMessageDialog(this, "Quote emailed to " + email + ":\n" + quote, "Email Sent", JOptionPane.INFORMATION_MESSAGE);
            contactAgentButton.setEnabled(true);
            emailQuoteButton.setEnabled(false);
            break;
        }
    }

    private static PremiumDetails calculatePremium(int age, int drivingYears, boolean hasAccidents) {
        double basePremium = 500.0;
        if (age < 25) basePremium += 200.0;
        else if (age > 65) basePremium += 100.0;
        if (drivingYears < 5) basePremium += 150.0;
        if (hasAccidents) basePremium += 300.0;

        double fullPaymentDiscount = basePremium * 0.95;
        double downPayment = basePremium * 0.10;
        double remainingBalance = basePremium - downPayment;
        double monthlyPayment = remainingBalance / 6;

        return new PremiumDetails(basePremium, fullPaymentDiscount, downPayment, remainingBalance, monthlyPayment);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuoteCalculator frame = new QuoteCalculator();
            frame.setVisible(true);
        });
    }
}

class PremiumDetails {
    double basePremium;
    double fullPaymentDiscount;
    double downPayment;
    double remainingBalance;
    double monthlyPayment;

    PremiumDetails(double base, double full, double down, double remaining, double monthly) {
        this.basePremium = base;
        this.fullPaymentDiscount = full;
        this.downPayment = down;
        this.remainingBalance = remaining;
        this.monthlyPayment = monthly;
    }
}