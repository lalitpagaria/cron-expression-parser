package com.cron.parser;

import com.cron.parser.fieldparser.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CronExpressionBuilder {
    private static final int CRON_FIELDS = 5;
    private String[] expressionFields;

    public CronExpressionBuilder(String[] expressionFields) {
        this.expressionFields = expressionFields;
    }

    public CronExpression build() throws IllegalArgumentException {
        if (expressionFields == null || expressionFields.length <= CRON_FIELDS)
            throw new IllegalArgumentException("Invalid number of field arguments");

        Set<Integer> minuteFieldSet = new MinuteFieldParser().parseField(expressionFields[0]);
        Set<Integer> hourFieldSet = new HourFieldParser().parseField(expressionFields[1]);
        Set<Integer> dayOfMonthFieldSet = new DayOfMonthFieldParser().parseField(expressionFields[2]);
        Set<Integer> monthFieldSet = new MonthFieldParser().parseField(expressionFields[3]);
        Set<Integer> dayOfWeekFieldSet = new DayOfWeekFieldParser().parseField(expressionFields[4]);

        List<String> commandFieldList = new ArrayList<>(expressionFields.length - CRON_FIELDS);
        commandFieldList.addAll(Arrays.asList(expressionFields).subList(CRON_FIELDS, expressionFields.length));

        return new CronExpression(minuteFieldSet, hourFieldSet, dayOfMonthFieldSet, monthFieldSet, dayOfWeekFieldSet, commandFieldList);
    }
}