package calculator.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import calculator.CalculatorWriter;
import org.apache.commons.io.FileUtils;


public class FileCalculatorWriter implements CalculatorWriter {

    private static final String RESULT = "The calculating result: ";

    private final String outputFile;

    public FileCalculatorWriter(String outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void writeResult(String result) {
        try {
            FileUtils.writeStringToFile(new File(outputFile), result, Charset.defaultCharset());
        } catch (IOException ex) {
            System.out.println(RESULT + result);
            System.out.println(ex.getMessage());
        }
    }
}
