package com.cron.parser;

import org.junit.Assert;
import org.junit.Test;

public class CronExpressionBuilderTest {

    @Test
    public void correctCronExpression_shouldCreateObject_noException() {
        String[] expression = {"*/15", "0", "1,15", "*", "1-5", "/user/bin/find"};
        CronExpression cronExpression = new CronExpressionBuilder(expression).build();
        Assert.assertNotNull(cronExpression);
    }

    @Test
    public void manyArgumentToCommand_shouldCreateObject_noException() {
        String[] expression = {"*/15", "0", "1,15", "*", "1-5", "/user/bin/find", "test1", "test2"};
        CronExpression cronExpression = new CronExpressionBuilder(expression).build();
        Assert.assertNotNull(cronExpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidCronExpression_shouldThrowException_IllegalArgumentException() {
        String[] expression = {"*/15", "24", "1,15", "*", "1-5", "/user/bin/find"};
        CronExpression cronExpression = new CronExpressionBuilder(expression).build();
        Assert.assertNull(cronExpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyCommandField_shouldThrowException_IllegalArgumentException() {
        String[] expression = {"*/15", "24", "1,15", "*", "1-5"};
        CronExpression cronExpression = new CronExpressionBuilder(expression).build();
        Assert.assertNull(cronExpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lessNumberOfArgument_shouldThrowException_IllegalArgumentException() {
        String[] expression = {"*/15", "2", "1,15", "*", "1-5"};
        CronExpression cronExpression = new CronExpressionBuilder(expression).build();
        Assert.assertNull(cronExpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgument_shouldThrowException_IllegalArgumentException() {
        CronExpression cronExpression = new CronExpressionBuilder(null).build();
        Assert.assertNull(cronExpression);
    }
}