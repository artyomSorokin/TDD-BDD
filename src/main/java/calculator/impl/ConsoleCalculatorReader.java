package calculator.impl;

import java.util.Scanner;

import calculator.CalculatorReader;

public class ConsoleCalculatorReader implements CalculatorReader {

    @Override
    public String readExpression() {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.next();
        scanner.close();
        return expression;
    }
}
