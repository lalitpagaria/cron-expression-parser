package com.cron.parser.fieldparser;

import java.util.Set;
import java.util.TreeSet;

abstract public class FieldParser {
    protected static final String ALL_CHAR = "*";
    protected static final String RANGE_CHAR = "-";
    protected static final String STEP_CHAR = "/";
    protected static final String LIST_CHAR = ",";

    /**
     * This function parse single filed (minute, hour, doy of week, month, and day of month) of cron expression.
     * @param fieldExpression filed expression may be including special chars
     * @return Set of allowed values on which cron may trigger
     * @throws IllegalArgumentException In case of illegal input
     */
    public Set<Integer> parseField(String fieldExpression) throws IllegalArgumentException {
        if(fieldExpression == null || fieldExpression.length() == 0)
            throw new IllegalArgumentException("Field expression cannot be null or empty");

        Set<Integer> parsedResult = new TreeSet<>();

        if(fieldExpression.length() == 1 && fieldExpression.contains(ALL_CHAR)) {
            parseAllNumbers(parsedResult);
            return parsedResult;
        }

        // Split expression with LIST_CHAR to handle list at beginning
        String[] subExpressions = fieldExpression.split(LIST_CHAR);
        for(String subExpression: subExpressions) {
            if(subExpression.contains(RANGE_CHAR)) {
                parseRange(subExpression, parsedResult);
            } else if (subExpression.contains(STEP_CHAR)) {
                parseStep(subExpression, parsedResult);
            } else {
                parseNumber(subExpression, parsedResult);
            }
        }

        return parsedResult;
    }

    // Parse range expression
    protected void parseRange(String rangeStr, Set<Integer> numberSet) throws IllegalArgumentException {
        String[] rangeNumbers = rangeStr.split(RANGE_CHAR);
        if(rangeNumbers.length != 2) throw new IllegalArgumentException("Illegal range field");
        int start = parseNumber(rangeNumbers[0], NumberRangeType.NORMAL);
        int end = parseNumber(rangeNumbers[1], NumberRangeType.NORMAL);
        for(int i = start; i <= end; i++) numberSet.add(i);
    }

    // Parse step expression
    protected void parseStep(String stepStr, Set<Integer> numberSet) throws IllegalArgumentException {
        String[] stepNumbers = stepStr.split(STEP_CHAR);
        if(stepNumbers.length != 2) throw new IllegalArgumentException("Illegal step field");
        int start = getMin();
        if(!(stepNumbers[0].length() == 1 && stepNumbers[0].contains(ALL_CHAR)))
            start = parseNumber(stepNumbers[0], NumberRangeType.NORMAL);

        int step = parseNumber(stepNumbers[1], NumberRangeType.STEP);
        for(int i = start; i <= getMax(); i += step) numberSet.add(i);
    }

    // To get all the allowed number due to asterisk
    protected void parseAllNumbers(Set<Integer> numberSet) {
        for(int i = getMin(); i <= getMax(); i++) numberSet.add(i);
    }

    // Parse number and add to number set
    protected void parseNumber(String numStr, Set<Integer> numberSet) throws IllegalArgumentException {
        numberSet.add(parseNumber(numStr, NumberRangeType.NORMAL));
    }

    // Parse number according to number type
    protected int parseNumber(String numStr, NumberRangeType rangeType) throws IllegalArgumentException {
        try {
            int number = Integer.parseInt(numStr, 10);
            if(!isInRange(number, rangeType)) throw new IllegalArgumentException("Number not in range");
            return number;
        } catch (Exception ex){
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    // For normal case number should bound by min and max. For step case handle negative and divide by zero error
    private boolean isInRange(int number, NumberRangeType rangeType) {
        if(rangeType == NumberRangeType.STEP)
            return (number > 0);
        return (number >= getMin()) && (number <= getMax());
    }

    protected abstract int getMin();

    protected abstract int getMax();


    protected enum NumberRangeType {
        NORMAL,
        STEP
    }
}
