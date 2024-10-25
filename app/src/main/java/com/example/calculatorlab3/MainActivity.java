package com.example.calculatorlab3; // Package declaration for the MainActivity class

import android.os.Bundle; // import for Bundle, used to pass data between activities
import android.view.View; // import for View, the base class for widgets
import android.widget.Button; // import for Button, used to create button instances
import android.widget.TextView; // import for TextView, used for displaying text on the screen

import androidx.appcompat.app.AppCompatActivity; // import for AppCompatActivity, base class for activities
import androidx.core.graphics.Insets; // import for Insets, manages system inset values
import androidx.core.view.ViewCompat; //import for ViewCompat, helper for view functions
import androidx.core.view.WindowInsetsCompat; // import for WindowInsetsCompat, manages window insets

public class MainActivity extends AppCompatActivity {

    private TextView display; //TextView variable to show calculations and results
    private double memory = 0; // Memory storage for M+, M-, MR, and MS functionalities
    private double firstOperand = 0; // Stores the first operand for calculations
    private String currentOperator = ""; // Stores the current operator for calculations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call the superclass implementation
        setContentView(R.layout.activity_main); // set the layout for this activity from XML

        display = findViewById(R.id.displayTextView); // initialize the TextView with its ID from XML

        //configure the app to handle system inset (edge-to-edge) layouts
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); //get system bar insets
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); //apply padding to avoid system bars overlap
            return insets; // return insets to complete function
        });
    }
    public void onButtonClick(View view) { // handle all button clicks, triggered by XML onClick
        Button button = (Button) view; //cast the clicked view to a Button object
        String buttonTag = button.getTag().toString();

        // switch statement to determine which button was pressed
        switch (buttonTag) {
            case "MC": // memory clear button
                memory = 0;
                break;

            case "MR": // memory recall button
                display.setText(String.valueOf(memory));
                break;

            case "MS": // memory store button
                try {
                    memory = Double.parseDouble(display.getText().toString()); // store display value in memory
                } catch (NumberFormatException e) {
                    display.setText("Error");
                }
                break;

            case "MemoryAdd": // memory add button
                try {
                    memory += Double.parseDouble(display.getText().toString()); // add display value to memory
                } catch (NumberFormatException e) {
                    display.setText("Error");
                }
                break;

            case "MemorySubtract": // memory subtract button
                try {
                    memory -= Double.parseDouble(display.getText().toString()); // subtract display value from memory
                } catch (NumberFormatException e) {
                    display.setText("Error");
                }
                break;

            case "ClearEntry": // clear entry button
                display.setText(""); // clear the current entry in the display TextView
                break;

            case "Clear": // clear all button
                display.setText(""); // clear the display
                firstOperand = 0; // reset the first operand
                currentOperator = ""; // reset the current operator
                break;

            case "BackspaceDelete": // backspace button
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    display.setText(currentText.substring(0, currentText.length() - 1)); // remove last character
                }
                break;

            case "Equals": // equals button
                calculateResult();
                break;

            // cases for number buttons 0–9
            case "0":
                display.append("0");
                break;
            case "1":
                display.append("1");
                break;
            case "2":
                display.append("2");
                break;
            case "3":
                display.append("3");
                break;
            case "4":
                display.append("4");
                break;
            case "5":
                display.append("5");
                break;
            case "6":
                display.append("6");
                break;
            case "7":
                display.append("7");
                break;
            case "8":
                display.append("8");
                break;
            case "9":
                display.append("9");
                break;

            // cases for arithmetic operator buttons
            case "Addition":
            case "Subtraction":
            case "Multiplication":
            case "Division":
                selectOperator(buttonTag);
                break;

            case "Dot":
                if (!display.getText().toString().contains(".")) {
                    display.append(buttonTag);
                }
                break;

            case "SignChange":
                changeSign();
                break;

            case "SquareRoot":
                calculateSquareRoot();
                break;

            case "Reciprocal":
                calculateReciprocal();
                break;

            case "Percentage":
                calculatePercentage();
                break;

            default:
                display.append(buttonTag);
                break;
        }
    }
    private void selectOperator(String operator) {
        try {
            firstOperand = Double.parseDouble(display.getText().toString());
            currentOperator = operator;
            display.setText("");
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }
    private void changeSign() {
        try {
            double currentValue = Double.parseDouble(display.getText().toString());
            display.setText(String.valueOf(-currentValue)); // negate the current value
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }

    private void calculateReciprocal() {
        try {
            double currentValue = Double.parseDouble(display.getText().toString());
            if (currentValue == 0) {
                display.setText("Error"); // Handle division by zero
            } else {
                display.setText(String.valueOf(1 / currentValue)); // Calculate reciprocal
            }
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }

    private void calculatePercentage() {
        try {
            double currentValue = Double.parseDouble(display.getText().toString());
            display.setText(String.valueOf(currentValue / 100));
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }

    private void calculateSquareRoot() {
        try {
            double currentValue = Double.parseDouble(display.getText().toString());
            if (currentValue < 0) {
                display.setText("Error");
            } else {
                display.setText(String.valueOf(Math.sqrt(currentValue)));
            }
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }

    private void calculateResult() {
        try {
            double secondOperand = Double.parseDouble(display.getText().toString());
            double result = 0;

            switch (currentOperator) {
                case "Addition":
                    result = firstOperand + secondOperand;
                    break;
                case "Subtraction":
                    result = firstOperand - secondOperand;
                    break;
                case "Multiplication":
                    result = firstOperand * secondOperand;
                    break;
                case "Division":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                default:
                    display.setText("Error");
                    return;
            }

            display.setText(String.valueOf(result));
            firstOperand = result;
            currentOperator = "";
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }
}