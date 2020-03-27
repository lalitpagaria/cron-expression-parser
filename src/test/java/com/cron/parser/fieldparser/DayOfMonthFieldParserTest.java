package com.cron.parser.fieldparser;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class DayOfMonthFieldParserTest {
    FieldParser fieldParser;
    Set<Integer> valueSet;

    @Before
    public void setup() {
        fieldParser = new DayOfMonthFieldParser();
    }

    @Test
    public void validInput_correctMinAndMax_valueInRange() {
        assertEquals(1, fieldParser.getMin());
        assertEquals(31, fieldParser.getMax());
    }

    @Test
    public void validInput_onlyAsterisk_allValues() {
        valueSet = new TreeSet<>();
        for(int i = fieldParser.getMin(); i <= fieldParser.getMax(); i++)
            valueSet.add(i);
        assertEquals(valueSet, fieldParser.parseField("*"));
    }

    @Test
    public void validInput_onlyNumber_valueMatch() {
        valueSet = new TreeSet<>(Collections.singletonList(5));
        assertEquals(valueSet, fieldParser.parseField("5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_numberBelowMin_shouldThrowException() {
        fieldParser.parseField("0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_numberAboveMax_shouldThrowException() {
        fieldParser.parseField("60");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_badNumberFormat_shouldThrowException() {
        fieldParser.parseField("5A");
    }

    @Test
    public void validInput_onlyRange_valueMatch() {
        valueSet = new TreeSet<>();
        for(int i = fieldParser.getMin(); i <= fieldParser.getMax(); i++)
            valueSet.add(i);
        assertEquals(valueSet, fieldParser.parseField("1-31"));
    }

    @Test
    public void validInput_onlyStep_valueMatch() {
        valueSet = new TreeSet<>(Arrays.asList(1, 11, 21, 31));
        assertEquals(valueSet, fieldParser.parseField("1/10"));
    }

    @Test
    public void validInput_onlyStepWithAsterisk_valueMatch() {
        valueSet = new TreeSet<>(Arrays.asList(1, 16, 31));
        assertEquals(valueSet, fieldParser.parseField("*/15"));
    }

    @Test
    public void validInput_onlyList_valueMatch() {
        valueSet = new TreeSet<>(Arrays.asList(1, 4, 12));
        assertEquals(valueSet, fieldParser.parseField("1,4,12"));
    }

    @Test
    public void validInput_listRangeAndStep_valueMatch() {
        valueSet = new TreeSet<>(Arrays.asList(1, 4, 10, 11, 12, 5, 6, 7, 21, 31, 9, 29));
        assertEquals(valueSet, fieldParser.parseField("1,4,10-12,1/10,5-7,9/20"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_extraAsterisk_shouldThrowException() {
        fieldParser.parseField("*,4,10-12,1/10,5-7,*/20");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_withAsteriskInListField_shouldThrowException() {
        fieldParser.parseField("*,4,10-12,1/10,5-7,1/20");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidLessThanMinInRangeField_shouldThrowException() {
        fieldParser.parseField("0-12");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidMoreThanMaxInRangeField_shouldThrowException() {
        fieldParser.parseField("1-60");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidLessThanMinInStepField_shouldThrowException() {
        fieldParser.parseField("0/3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidMoreThanMaxInStepField_shouldThrowException() {
        fieldParser.parseField("32/1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_divideByZeroInStepField_shouldThrowException() {
        fieldParser.parseField("2/0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_negativeStepInStepField_shouldThrowException() {
        fieldParser.parseField("2/-1");
    }

    @Test
    public void validInput_moreThanMaxStepStepInStepField_shouldThrowException() {
        valueSet = new TreeSet<>(Collections.singletonList(5));
        assertEquals(valueSet, fieldParser.parseField("5/100"));
    }
}