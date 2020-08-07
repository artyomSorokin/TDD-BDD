package calculator.impl;

import calculator.CalculatorWriter;

public class ConsoleCalculatorWriter implements CalculatorWriter {

    @Override
    public void writeResult(String result) {
        System.out.println(result);
    }
}
