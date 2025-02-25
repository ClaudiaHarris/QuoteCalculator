import java.util.Scanner;

public class QuoteCalculator {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

     // Get user input and validate
     int age = getValidInt(scanner, "Enter your age: ");
     
     int drivingYears = getValidInt(scanner, "Enter years of driving experience: ");
    
     boolean hasAccidents = getValidYesNo(scanner, "Have you had any accidents in the last 5 years? (yes/no): ");
        
     double basePremium = 500.0;

     // Adjustments to premium amount
     if (age < 25) {
      basePremium += 200.0; // Young drivers surcharge
     } else if (age > 65) {
        basePremium += 100.0; // Elderly drivers surcharge
      }
     if (drivingYears < 5) {
      basePremium += 150.0; // Inexperienced driver surcharge
     }
     if (hasAccidents) {
      basePremium += 300.0; // High-risk driver penalty
     }

    // Calculations
    double fullPaymentDiscount = basePremium * 0.95; //a 5% discount if six-month premium is paid in full
    double downPayment = basePremium * 0.10; //a 10% downpayment if a monthly plan is setup
    double remainingBalance = basePremium - downPayment; // the amount that is divided into 6 monthly payments, after downpayment is subtracted
    double monthlyPayment = remainingBalance / 6; //monthly premium payments

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
    int attempts = 0;
    int maxAttempts = 5;
  
    while (attempts < maxAttempts) { //a loop but with a max number of attempts
      System.out.print(prompt);
      if (scanner.hasNextInt()) {
        int value = scanner.nextInt();
        scanner.nextLine(); //consuming the newline to clear the buffer for the next input
        if (value >= 0) {
          return value;
        } else {
          System.out.println("Error: Please enter a valid positive number.");
        }
      } else {
        System.out.println("Error: Invalid input. Please enter a number.");
        scanner.nextLine(); // Clear invalid input
      }
      attempts++; //control max attempts to offer an exit
    }
    System.out.println("Too many invalid attempts. Exiting.");
    System.exit(1); //exit on failure
    return -1; //compilers like this
  }

  private static boolean getValidYesNo (Scanner scanner, String prompt) {
    int attempts = 0;
    int maxAttempts = 5;
    while (attempts < maxAttempts ) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim().toLowerCase();
      if (input.equals("yes") || input.equals("y")) {
        return true;
      } else if (input.equals("no") || input.equals("n")) {
          return false;
        } else {
          System.out.println("Error: Please enter 'yes' or 'no' (or 'y'/'n').");
        }
        attempts++;
      }
      System.out.println("Too many invalid attempts. Assuming 'yes'.");
      return true;
    }

  }

