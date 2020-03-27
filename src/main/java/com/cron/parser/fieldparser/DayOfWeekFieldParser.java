package com.cron.parser.fieldparser;

public class DayOfWeekFieldParser extends FieldParser {
    @Override
    protected int getMin() {
        return 0;
    }

    @Override
    protected int getMax() {
        return 6;
    }
}
