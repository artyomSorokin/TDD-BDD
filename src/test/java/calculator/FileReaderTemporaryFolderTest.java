package calculator;

import calculator.impl.FileCalculatorReader;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class FileReaderTemporaryFolderTest {

    private static final String EXPRESSION = "5+7*3-10";
    private CalculatorReader calculatorReader;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testReadExpressionFromFile() throws IOException {
        File file = folder.newFile();
        FileUtils.writeStringToFile(file, EXPRESSION, Charset.defaultCharset());
        calculatorReader = new FileCalculatorReader(file.getAbsolutePath());
        assertEquals(EXPRESSION, calculatorReader.readExpression());
    }
}
