package com.cron.parser.fieldparser;

public class MinuteFieldParser extends FieldParser {
    @Override
    protected int getMin() {
        return 0;
    }

    @Override
    protected int getMax() {
        return 59;
    }
}
