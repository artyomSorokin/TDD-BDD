package calculator;

import calculator.impl.ReversePolishNotationCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParameterizedConsoleTest {

    private static final int SCALE = 1;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private Calculator calculatorRPN;

    @Before
    public void init() {
        calculatorRPN = new ReversePolishNotationCalculator(SCALE, ROUNDING_MODE);
    }

    @Parameterized.Parameter(0)
    public String expression;

    @Parameterized.Parameter(1)
    public String expected;

    @Parameterized.Parameters
    public static List<Object[]> data() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{"1+2", "3.0"});
        data.add(new Object[]{"1-2", "-1.0"});
        data.add(new Object[]{"5/5", "1.0"});
        data.add(new Object[]{"2+5*4/2+14", "26.0"});
        data.add(new Object[]{"15*3+24/8*2", "51.0"});
        data.add(new Object[]{"115-5*3-50*2", "0.0"});
        data.add(new Object[]{"95+(5-10)+(-3*5)", "75.0"});
        data.add(new Object[]{"35*2/7-(5-10)", "15.0"});
        return data;
    }

    @Test
    public void testCalculator() {
        BigDecimal actual = calculatorRPN.calculate(expression);
        assertEquals(expected, String.valueOf(actual));
    }
}
