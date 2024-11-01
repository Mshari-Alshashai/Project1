import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculator {

    private List<Double> results;
    private Scanner scanner;

    public Calculator() {
        results = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("Calculator Menu:");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println("5. Modulus");
        System.out.println("6. Minimum");
        System.out.println("7. Maximum");
        System.out.println("8. Average");
        System.out.println("9. Print last result");
        System.out.println("10. Print all results");
        System.out.println("0. Exit");
    }

    public void run() {
        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Exiting the calculator. Goodbye!");
                break;
            }

            switch (choice) {
                case 1:
                    performAddition();
                    break;
                case 2:
                    performSubtraction();
                    break;
                case 3:
                    performMultiplication();
                    break;
                case 4:
                    performDivision();
                    break;
                case 5:
                    performModulus();
                    break;
                case 6:
                    findMinimum();
                    break;
                case 7:
                    findMaximum();
                    break;
                case 8:
                    calculateAverage();
                    break;
                case 9:
                    printLastResult();
                    break;
                case 10:
                    printAllResults();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private void performAddition() {
        double[] numbers = getTwoNumbers();
        double result = numbers[0] + numbers[1];
        results.add(result);
        System.out.println("Result: " + result);
    }

    private void performSubtraction() {
        double[] numbers = getTwoNumbers();
        double result = numbers[0] - numbers[1];
        results.add(result);
        System.out.println("Result: " + result);
    }

    private void performMultiplication() {
        double[] numbers = getTwoNumbers();
        double result = numbers[0] * numbers[1];
        results.add(result);
        System.out.println("Result: " + result);
    }

    private void performDivision() {
        double[] numbers = getTwoNumbers();
        if (numbers[1] == 0) {
            System.out.println("Error: Division by zero.");
            return;
        }
        double result = numbers[0] / numbers[1];
        results.add(result);
        System.out.println("Result: " + result);
    }

    private void performModulus() {
        double[] numbers = getTwoNumbers();
        double result = numbers[0] % numbers[1];
        results.add(result);
        System.out.println("Result: " + result);
    }

    private void findMinimum() {
        double[] numbers = getMultipleNumbers();
        double min = numbers[0];
        for (double number : numbers) {
            if (number < min) {
                min = number;
            }
        }
        results.add(min);
        System.out.println("Minimum: " + min);
    }

    private void findMaximum() {
        double[] numbers = getMultipleNumbers();
        double max = numbers[0];
        for (double number : numbers) {
            if (number > max) {
                max = number;
            }
        }
        results.add(max);
        System.out.println("Maximum: " + max);
    }

    private void calculateAverage() {
        double[] numbers = getMultipleNumbers();
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        double average = sum / numbers.length;
        results.add(average);
        System.out.println("Average: " + average);
    }

    private void printLastResult() {
        if (results.isEmpty()) {
            System.out.println("No results available.");
        } else {
            System.out.println("Last result: " + results.get(results.size() - 1));
        }
    }

    private void printAllResults() {
        if (results.isEmpty()) {
            System.out.println("No results available.");
        } else {
            System.out.println("All results:");
            for (Double result : results) {
                System.out.println(result);
            }
        }
    }

    private double[] getTwoNumbers() {
        System.out.print("Enter first number: ");
        double firstNumber = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double secondNumber = scanner.nextDouble();
        return new double[]{firstNumber, secondNumber};
    }

    private double[] getMultipleNumbers() {
        System.out.print("Enter the number of values: ");
        int count = scanner.nextInt();
        double[] numbers = new double[count];
        for (int i = 0; i < count; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            numbers[i] = scanner.nextDouble();
        }
        return numbers;
    }

    public static void main(String[] args) {
        Calculator cal = new Calculator();
        cal.run();
    }
}

