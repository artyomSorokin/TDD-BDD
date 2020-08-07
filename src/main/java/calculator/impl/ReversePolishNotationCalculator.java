package calculator.impl;

import calculator.Calculator;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReversePolishNotationCalculator implements Calculator {

    private static final String EMPTY_EXP = "The specified expression is empty";
    private static final String UNSUPPORTED_OPERATIONS = "The specified expression is incorrect or it has unsupported operations";

    private static final String LEFT_BRACE = "(";
    private static final String RIGHT_BRACE = ")";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "/";
    private static final String MINUS = "-";
    private static final String PLUS = "+";
    private static final String MATH_EXP_PATTERN =
                    "\\s*(\\s*-?\\s*\\d+\\s*|" +
                    "(\\((\\s*\\d+\\s*[/\\-+*\\s]\\s*\\d+\\s*)" +
                    "(\\s*[/\\-+*\\s]\\s*\\d+\\s*)*\\)))" +
                    "(\\s*[/\\-+*\\s]\\s*(\\(\\s*-?\\s*\\d+\\s*\\)|\\s*\\d+\\s*|" +
                    "(\\((\\s*-?\\s*\\d+\\s*[/\\-+*\\s]\\s*\\d+\\s*)" +
                    "(\\s*[/\\-+*\\s]\\s*\\d+\\s*)*\\s*\\)\\s*)\\s*)\\s*)*" +
                    "|\\s*-?\\s*\\(\\s*-?\\s*\\d\\s*\\)\\s*";

    private static final Map<String, Integer> OPERATION_PRIORITY_MAP;
    private static final Set<String> OPERATIONS;

    static {
        OPERATION_PRIORITY_MAP = new HashMap<>();
        OPERATION_PRIORITY_MAP.put(LEFT_BRACE, 1);
        OPERATION_PRIORITY_MAP.put(MULTIPLY, 2);
        OPERATION_PRIORITY_MAP.put(DIVIDE, 2);
        OPERATION_PRIORITY_MAP.put(MINUS, 3);
        OPERATION_PRIORITY_MAP.put(PLUS, 3);

        OPERATIONS = OPERATION_PRIORITY_MAP.keySet();
    }

    private final int scale;
    private final RoundingMode roundingMode;

    public ReversePolishNotationCalculator(int scale, RoundingMode roundingMode) {
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    /**
     * Use this method to calculate expression.
     *
     * @param expression as a string.
     * @return result of expression as BigDecimal.
     */
    public BigDecimal calculate(String expression) {
        checkIfValidMathExpression(expression);

        String rpn = infixToRPN(expression);
        String[] elements = rpn.split(StringUtils.SPACE);
        Deque<BigDecimal> result = new ArrayDeque<>();

        for (String element : elements) {
            if (!OPERATIONS.contains(element)) {
                result.push(new BigDecimal(element));
            } else {
                performOperation(result, element);
            }
        }
        return result.pop().setScale(scale, roundingMode);
    }

    private void checkIfValidMathExpression(String expression) {
        if (StringUtils.isEmpty(expression)) {
            throw new IllegalArgumentException(EMPTY_EXP);
        } else if (!expression.matches(MATH_EXP_PATTERN)) {
            throw new IllegalArgumentException(UNSUPPORTED_OPERATIONS);
        }
    }

    private String infixToRPN(String expression) {
        ExpressionInfoKeeper infoKeeper = new ExpressionInfoKeeper(expression);
        while (true) {
            resetCurrentOperation(infoKeeper);
            findAndSetNextOperation(infoKeeper);

            if (isExpressionParsed(infoKeeper)) {
                break;
            } else {
                handleOperation(infoKeeper);
            }
        }
        handleRemainedOperationAndNumber(infoKeeper);
        return buildInfixNotation(infoKeeper);
    }

    private void performOperation(Deque<BigDecimal> result, String element) {
        BigDecimal operand2 = result.pop();
        BigDecimal operand1 = result.isEmpty() ? BigDecimal.ZERO : result.pop();

        switch (element) {
            case MULTIPLY:
                result.push(operand1.multiply(operand2));
                break;
            case DIVIDE:
                result.push(operand1.divide(operand2, scale, roundingMode));
                break;
            case PLUS:
                result.push(operand1.add(operand2));
                break;
            case MINUS:
                result.push(operand1.subtract(operand2));
                break;
            default:
                throw new IllegalArgumentException(UNSUPPORTED_OPERATIONS);
        }
    }

    private void resetCurrentOperation(ExpressionInfoKeeper infoKeeper) {
        infoKeeper.currOpIndex = infoKeeper.expression.length();
        infoKeeper.currOp = StringUtils.EMPTY;
    }

    private boolean isExpressionParsed(ExpressionInfoKeeper infoKeeper) {
        return infoKeeper.currOpIndex == infoKeeper.expression.length();
    }

    private void findAndSetNextOperation(ExpressionInfoKeeper infoKeeper) {
        for (String operation : OPERATIONS) {
            int index = infoKeeper.expression.indexOf(operation, infoKeeper.prevOpIndex);
            if (index >= 0 && index < infoKeeper.currOpIndex) {
                infoKeeper.currOp = operation;
                infoKeeper.currOpIndex = index;
            }
        }
    }

    private void handleOperation(ExpressionInfoKeeper infoKeeper) {
        if (infoKeeper.prevOpIndex != infoKeeper.currOpIndex) {
            String numberBeforeOp = infoKeeper.expression.substring(infoKeeper.prevOpIndex, infoKeeper.currOpIndex);
            infoKeeper.listRPN.add(numberBeforeOp);
        }
        if (infoKeeper.currOp.equals(LEFT_BRACE)) {
            handleBraces(infoKeeper);
        } else {
            addOperation(infoKeeper);
        }
        infoKeeper.prevOpIndex = infoKeeper.currOpIndex + infoKeeper.currOp.length();
    }

    private void addOperation(ExpressionInfoKeeper infoKeeper) {
        while (!infoKeeper.operationStack.isEmpty() && isPriorityOperation(infoKeeper)) {
            infoKeeper.listRPN.add(infoKeeper.operationStack.pop());
        }
        infoKeeper.operationStack.push(infoKeeper.currOp);
    }

    private void handleBraces(ExpressionInfoKeeper infoKeeper) {
        String expressionInBraces = extractExpressionFromBraces(infoKeeper.expression);
        String expressionResult = String.valueOf(calculate(expressionInBraces));
        String substringToRemove = LEFT_BRACE + expressionInBraces + RIGHT_BRACE;

        infoKeeper.listRPN.add(expressionResult);
        infoKeeper.expression = infoKeeper.expression.replace(substringToRemove, StringUtils.EMPTY);
        infoKeeper.currOpIndex--;
    }

    private String extractExpressionFromBraces(String expression) {
        return expression.substring(expression.indexOf(LEFT_BRACE) + 1, expression.indexOf(RIGHT_BRACE));
    }

    private boolean isPriorityOperation(ExpressionInfoKeeper infoKeeper) {
        return OPERATION_PRIORITY_MAP.get(infoKeeper.currOp)
                >= OPERATION_PRIORITY_MAP.get(infoKeeper.operationStack.peek());
    }

    private void handleRemainedOperationAndNumber(ExpressionInfoKeeper infoKeeper) {
        if (infoKeeper.prevOpIndex < infoKeeper.expression.length()) {
            String remainedNumber = infoKeeper.expression.substring(infoKeeper.prevOpIndex);
            infoKeeper.listRPN.add(remainedNumber);
        }
        while (!infoKeeper.operationStack.isEmpty()) {
            infoKeeper.listRPN.add(infoKeeper.operationStack.pop());
        }
    }

    private String buildInfixNotation(ExpressionInfoKeeper infoKeeper) {
        StringBuilder rpn = new StringBuilder();
        if (!infoKeeper.listRPN.isEmpty()) {
            rpn.append(infoKeeper.listRPN.remove(0));
        }
        while (!infoKeeper.listRPN.isEmpty()) {
            rpn.append(StringUtils.SPACE).append(infoKeeper.listRPN.remove(0));
        }
        return rpn.toString();
    }

    private class ExpressionInfoKeeper {

        int prevOpIndex;
        int currOpIndex;
        String currOp;
        String expression;
        List<String> listRPN;
        Deque<String> operationStack;

        private ExpressionInfoKeeper(String expression) {
            this.expression = expression.replaceAll(StringUtils.SPACE, StringUtils.EMPTY);
            prevOpIndex = 0;
            listRPN = new ArrayList<>();
            operationStack = new ArrayDeque<>();
        }
    }
}
