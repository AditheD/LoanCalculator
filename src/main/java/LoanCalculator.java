
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.NumberFormat;

/**
 * A JavaFX program that calculates the monthly payment and total payment for a loan.
 *
 * @author Adithe Das
 */
public class LoanCalculator extends Application {
    private final TextField annualInterestRateField = new TextField();
    private final TextField numberOfYearsField = new TextField();
    private final TextField loanAmountField = new TextField();

    // Text fields used to display the results
    private final TextField monthlyPaymentField = new TextField();
    private final TextField totalPaymentField = new TextField();

    @Override
    public void start(Stage primaryStage) {

        // Create the layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Add the labels and text fields
        gridPane.add(new Label("Annual Interest Rate:"), 0, 0);
        gridPane.add(annualInterestRateField, 1, 0);

        gridPane.add(new Label("Number of Years:"), 0, 1);
        gridPane.add(numberOfYearsField, 1, 1);

        gridPane.add(new Label("Loan Amount:"), 0, 2);
        gridPane.add(loanAmountField, 1, 2);

        gridPane.add(new Label("Monthly Payment:"), 0, 3);
        gridPane.add(monthlyPaymentField, 1, 3);

        gridPane.add(new Label("Total Payment:"), 0, 4);
        gridPane.add(totalPaymentField, 1, 4);

        // The result fields should not be edited by the user
        monthlyPaymentField.setEditable(false);
        totalPaymentField.setEditable(false);

        // Align numbers on the right side of the text fields
        annualInterestRateField.setAlignment(Pos.CENTER_RIGHT);
        numberOfYearsField.setAlignment(Pos.CENTER_RIGHT);
        loanAmountField.setAlignment(Pos.CENTER_RIGHT);
        monthlyPaymentField.setAlignment(Pos.CENTER_RIGHT);
        totalPaymentField.setAlignment(Pos.CENTER_RIGHT);

        // Create the calculation button
        Button computeButton = new Button("Calculator");
        gridPane.add(computeButton, 1, 5);
        GridPane.setHalignment(computeButton, javafx.geometry.HPos.RIGHT);

        /**
         * Event-driven programming: this code runs when the user clicks the button.
         */
        computeButton.setOnAction(event -> calculateLoanPayment());

        // Create and display the scene
        Scene scene = new Scene(gridPane, 400, 275);

        primaryStage.setTitle("LoanCalculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Reads the user's input and calculates the monthly payment and total payment.
     */
    private void calculateLoanPayment() {
        try {
            // Read the values entered by the user
            double annualInterestRate = Double.parseDouble(annualInterestRateField.getText());

            int numberOfYears = Integer.parseInt(numberOfYearsField.getText());

            double loanAmount = Double.parseDouble(loanAmountField.getText());

            // Validate the values
            if (annualInterestRate < 0 || numberOfYears <= 0 || loanAmount <= 0) {

                throw new IllegalArgumentException();
            }

            // Convert the annual percentage rate to a monthly decimal rate
            double monthlyInterestRate = annualInterestRate / 1200.0;

            // Find the total number of monthly payments
            int numberOfPayments = numberOfYears * 12;

            double monthlyPayment;

            // Handle a loan with a 0% interest rate
            if (monthlyInterestRate == 0) {
                monthlyPayment = loanAmount / numberOfPayments;
            } else {
                monthlyPayment = loanAmount * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
            }

            // Calculate the total amount paid
            double totalPayment = monthlyPayment * numberOfPayments;

            NumberFormat currency = NumberFormat.getCurrencyInstance();

            monthlyPaymentField.setText(currency.format(monthlyPayment));

            totalPaymentField.setText(currency.format(totalPayment));

        } catch (NumberFormatException exception) {
            showErrorMessage("Please enter numbers in all three input fields.");
        } catch (IllegalArgumentException exception) {
            showErrorMessage("The loan amount and years must be greater than 0. " + "The interest rate cannot be negative.");
        }
    }

    /**
     * Displays an error message when the input is invalid.
     *
     * @param message the message shown to the user
     */
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Please check your information.");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Starts the JavaFX application.
     */
   public static void main(String[] args) {
     launch(args);
   }
}
