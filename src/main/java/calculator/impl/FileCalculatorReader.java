package calculator.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import calculator.CalculatorReader;
import org.apache.commons.io.FileUtils;


public class FileCalculatorReader implements CalculatorReader {

    private final String inputFile;

    public FileCalculatorReader(String inputFile) {
        this.inputFile = inputFile;
    }

    @Override
    public String readExpression() {
        try {
            return FileUtils.readFileToString(new File(inputFile), Charset.defaultCharset());
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
