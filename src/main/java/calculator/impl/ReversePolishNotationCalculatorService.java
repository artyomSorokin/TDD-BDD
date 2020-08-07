package calculator.impl;

import calculator.CalculatorService;
import calculator.Calculator;
import calculator.CalculatorReader;
import calculator.CalculatorWriter;

/**
 * ReversePolishNotationCalculatorService class to calculate expressions.
 */
public class ReversePolishNotationCalculatorService implements CalculatorService {

    private CalculatorReader calculatorReader;
    private CalculatorWriter calculatorWriter;
    private Calculator calculator;

    /**
     * Constructor with parameters.
     *
     * @param calculatorReader instance to read expressions.
     * @param calculatorWriter instance to write results.
     * @param calculator       to calculate expressions.
     */
    public ReversePolishNotationCalculatorService(CalculatorReader calculatorReader,
                                                  CalculatorWriter calculatorWriter,
                                                  Calculator calculator) {
        this.calculatorReader = calculatorReader;
        this.calculatorWriter = calculatorWriter;
        this.calculator = calculator;
    }

    @Override
    public void calculateAndWriteResult() {
        String result;
        try {
            String expression = calculatorReader.readExpression();
            result = calculate(calculator, expression);
        } catch (IllegalArgumentException ex) {
            result = ex.getMessage();
        }
        calculatorWriter.writeResult(result);
    }

    private String calculate(Calculator calculator, String expression) {
        String result;
        try {
            result = String.valueOf(calculator.calculate(expression));
        } catch (IllegalArgumentException ex) {
            result = ex.getMessage();
        } catch (ArithmeticException ex) {
            result = "There is an arithmetic error in the expression: " + ex.getMessage();
        }
        return result;
    }
}
