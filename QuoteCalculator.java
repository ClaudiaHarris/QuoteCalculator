import java.util.Scanner;

public class QuoteCalculator {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

     // Get user input
     int age = getValidInt(scanner, "Enter your age: ");
     scanner.nextLine(); // Consume leftover newline

     int drivingYears = getValidInt(scanner, "Enter years of driving experience: ");
     scanner.nextLine(); // Consume leftover newline

     System.out.print("Have you had any accidents in the last 5 years? Enter yes or no: ");
     boolean hasAccidents = scanner.next().trim().equalsIgnoreCase("yes");

     double basePremium = 500.0;

     // Adjustments
     if (age < 25) {
      basePremium += 200.0; // Young drivers
     } else if (age > 65) {
        basePremium += 100.0; // Elderly drivers
      }
     if (drivingYears < 5) {
      basePremium += 150.0; // Inexperienced driver
     }
     if (hasAccidents) {
      basePremium += 300.0; // High-risk driver
     }

    // Calculations
    double fullPaymentDiscount = basePremium - (basePremium * 0.05);
    double downPayment = basePremium * 0.10;
    double remainingBalance = basePremium - downPayment;
    double monthlyPayment = remainingBalance / 6;

    // Output
    System.out.printf("\n--- Payment Breakdown ---\n");
    System.out.printf("Total Premium: $%.2f%n", basePremium);
    System.out.printf("With a 5%% discount for full payment: $%.2f%n", fullPaymentDiscount);
    System.out.printf("Down Payment (due today): $%.2f%n", downPayment);
    System.out.printf("Remaining Balance: $%.2f%n", remainingBalance);
    System.out.printf("6 Monthly Payments of: $%.2f each%n", monthlyPayment);

    // Ask if the user wants to speak to an agent
    scanner.nextLine(); // Consume leftover newline
    System.out.println("\nWould you like to speak to an agent? (yes/no)");
    String speakToAgent = scanner.nextLine().trim().toLowerCase();
    boolean toAgent = speakToAgent.equals("yes") || speakToAgent.equals("y");

    if (toAgent) {
        System.out.println("An agent will contact you shortly.");
    } else {
        System.out.println("Thank you for using our quote calculator!");
    }

    scanner.close();
  }
  
  // Function to validate integer input
  private static int getValidInt(Scanner scanner, String prompt) {
    int value;
    while (true) {
      System.out.print(prompt);
      if (scanner.hasNextInt()) {
        value = scanner.nextInt();
        if (value >= 0) {
          return value;
        } else {
          System.out.println("Error: Please enter a valid positive number.");
        }
      } else {
        System.out.println("Error: Invalid input. Please enter a number.");
        scanner.next(); // Clear invalid input
      }
    }
  }
}
