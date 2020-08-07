package calculator;

import java.math.RoundingMode;
import java.util.Objects;

import calculator.impl.ConsoleCalculatorReader;
import calculator.impl.ConsoleCalculatorWriter;
import calculator.impl.FileCalculatorReader;
import calculator.impl.FileCalculatorWriter;
import calculator.impl.ReversePolishNotationCalculator;
import calculator.impl.ReversePolishNotationCalculatorService;

public class CalculatorApplication {

    private static final String inputFileExpression = "src/main/resources/InputFileExpression.txt";
    private static final String outputFileResult = "src/main/resources/OutputFileResult.txt";
    private static final int SCALE = 1;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * Method main for expression calculation.
     *
     * @param args must be empty or have 1 or more parameters, if args is empty, then works in console mode.
     *             else works with input file
     */
    public static void main(String[] args) {
        new CalculatorApplication().startCalculator(args);
    }

    private static CalculatorService createFileCalculationApp(String inputFile, String outputFile) {
        CalculatorReader calculatorReader = new FileCalculatorReader(inputFile);
        CalculatorWriter calculatorWriter = new FileCalculatorWriter(outputFile);
        Calculator calculator = new ReversePolishNotationCalculator(SCALE, ROUNDING_MODE);
        return new ReversePolishNotationCalculatorService(calculatorReader, calculatorWriter, calculator);
    }

    private static CalculatorService createConsoleCalculationApp() {
        CalculatorReader calculatorReader = new ConsoleCalculatorReader();
        CalculatorWriter calculatorWriter = new ConsoleCalculatorWriter();
        Calculator calculator = new ReversePolishNotationCalculator(SCALE, ROUNDING_MODE);
        return new ReversePolishNotationCalculatorService(calculatorReader, calculatorWriter, calculator);
    }

    private void startCalculator(String[] args) {
        CalculatorService calculationService;
        if (Objects.isNull(args) || args.length == 0) {
            calculationService = createConsoleCalculationApp();
        } else {
            calculationService = createFileCalculationApp(inputFileExpression, outputFileResult);
        }
        calculationService.calculateAndWriteResult();
    }
}
