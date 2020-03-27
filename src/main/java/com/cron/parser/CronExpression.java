package com.cron.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class CronExpression {
    private Set<Integer> minuteFieldSet;
    private Set<Integer> hourFieldSet;
    private Set<Integer> dayOfMonthFieldSet;
    private Set<Integer> monthFieldSet;
    private Set<Integer> dayOfWeekFieldSet;
    private List<String> commandFieldList;

    private CronExpression() {}

    public void printExpression() {
        System.out.printf("minute          %s\n", minuteFieldSet.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.printf("hour            %s\n", hourFieldSet.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.printf("day of month    %s\n", dayOfMonthFieldSet.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.printf("month           %s\n", monthFieldSet.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.printf("day of week     %s\n", dayOfWeekFieldSet.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.printf("command         %s\n", commandFieldList.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
    }
}
