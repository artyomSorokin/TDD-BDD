package calculator;

import calculator.impl.FileCalculatorWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FileCalculatorWriterTest {

    private static final String expression = "5*3+8/2+(8-3)";

    @Spy
    private FileCalculatorWriter fileCalculatorWriter = new FileCalculatorWriter("src/fileTest.txt");

    @Test
    public void testWriteResult() {
        fileCalculatorWriter.writeResult(expression);
        verify(fileCalculatorWriter).writeResult(any(String.class));
    }
}
