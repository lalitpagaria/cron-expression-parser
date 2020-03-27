package com.cron.parser;

public class ParserMainApplication {
    public static void main(String[] args) {
        try {
            CronExpression cronExpression = new CronExpressionBuilder(args).build();
            cronExpression.printExpression();
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: Unable to parse expression due to " + ex.getMessage());
            System.exit(1);
        }
    }
}
