package com.cron.parser.fieldparser;

public class DayOfMonthFieldParser extends FieldParser {
    @Override
    protected int getMin() {
        return 1;
    }

    @Override
    protected int getMax() {
        return 31;
    }
}
