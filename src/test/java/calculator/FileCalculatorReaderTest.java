package calculator;

import calculator.impl.FileCalculatorReader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class FileCalculatorReaderTest {

    private FileCalculatorReader fileCalculatorReader;

    @Before
    public void init() {
        fileCalculatorReader = new FileCalculatorReader("File.txt");
    }

    @Rule
    public CustomExceptionTestRule customException = new CustomExceptionTestRule();

    @Test
    public void testReadExpressionWithException() {
        customException.setExpectedException(new IllegalArgumentException("File 'File.txt' does not exist"));
        when(fileCalculatorReader.readExpression()).thenThrow(new IOException());
        fileCalculatorReader.readExpression();
    }
}
