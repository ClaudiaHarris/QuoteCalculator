import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuoteCalculator extends JFrame {
    private JTextField ageField, drivingYearsField;
    private JCheckBox accidentCheckBox;
    private JLabel basePremiumLabel, fullPaymentLabel, downPaymentLabel, remainingLabel, monthlyLabel, agentLabel;
    private JButton contactAgentButton;

    public QuoteCalculator() {
        setTitle("Insurance Quote Calculator");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2, 10, 10)); // Increased to 10 rows

        // Inputs
        add(new JLabel("Age:"));
        ageField = new JTextField(5);
        add(ageField);

        add(new JLabel("Driving Years:"));
        drivingYearsField = new JTextField(5);
        add(drivingYearsField);

        add(new JLabel("Accidents in Last 5 Years?"));
        accidentCheckBox = new JCheckBox();
        add(accidentCheckBox);

        // Calculate button
        JButton calculateButton = new JButton("Calculate Quote");
        add(calculateButton);

        // Output labels
        add(new JLabel("")); // Spacer
        basePremiumLabel = new JLabel("Total Premium: $0.00");
        add(basePremiumLabel);

        fullPaymentLabel = new JLabel("Full Payment (5% off): $0.00");
        add(fullPaymentLabel);

        downPaymentLabel = new JLabel("Down Payment (10%): $0.00");
        add(downPaymentLabel);

        remainingLabel = new JLabel("Remaining Balance: $0.00");
        add(remainingLabel);

        monthlyLabel = new JLabel("6 Monthly Payments: $0.00");
        add(monthlyLabel);

        // Agent button and label
        contactAgentButton = new JButton("Contact Agent");
        contactAgentButton.setEnabled(false); // Disabled until calculation
        add(contactAgentButton);

        agentLabel = new JLabel("");
        add(agentLabel);

        // Button actions
        calculateButton.addActionListener(e -> calculateAndDisplay());
        contactAgentButton.addActionListener(e -> showAgentPrompt());
    }

    private void calculateAndDisplay() {
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            int drivingYears = Integer.parseInt(drivingYearsField.getText().trim());
            if (age < 0 || drivingYears < 0) {
                agentLabel.setText("Error: Use positive numbers.");
                contactAgentButton.setEnabled(false);
                return;
            }
            boolean hasAccidents = accidentCheckBox.isSelected();

            PremiumDetails details = calculatePremium(age, drivingYears, hasAccidents);

            basePremiumLabel.setText(String.format("Total Premium: $%.2f", details.basePremium));
            fullPaymentLabel.setText(String.format("Full Payment (5%% off): $%.2f", details.fullPaymentDiscount));
            downPaymentLabel.setText(String.format("Down Payment (10%%): $%.2f", details.downPayment));
            remainingLabel.setText(String.format("Remaining Balance: $%.2f", details.remainingBalance));
            monthlyLabel.setText(String.format("6 Monthly Payments: $%.2f", details.monthlyPayment));

            agentLabel.setText(""); // Clear previous message
            contactAgentButton.setEnabled(true); // Enable agent button
        } catch (NumberFormatException ex) {
            agentLabel.setText("Error: Enter valid numbers.");
            contactAgentButton.setEnabled(false);
        }
    }

    private void showAgentPrompt() {
        int choice = JOptionPane.showConfirmDialog(this, "Would you like to speak to an agent?", "Agent Contact", JOptionPane.YES_NO_OPTION);
        agentLabel.setText(choice == JOptionPane.YES_OPTION ? "An agent will contact you." : "Thank you!");
        contactAgentButton.setEnabled(false); // Disable after use
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