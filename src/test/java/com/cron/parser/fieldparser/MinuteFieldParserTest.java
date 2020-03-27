package com.cron.parser.fieldparser;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class MinuteFieldParserTest {
    FieldParser fieldParser;
    Set<Integer> valueSet;

    @Before
    public void setup() {
        fieldParser = new MinuteFieldParser();
    }

    @Test
    public void validInput_correctMinAndMax_valueInRange() {
        assertEquals(0, fieldParser.getMin());
        assertEquals(59, fieldParser.getMax());
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
        fieldParser.parseField("-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_numberAboveMax_shouldThrowException() {
        fieldParser.parseField("60");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_badNumberFormat_shouldThrowException() {
        fieldParser.parseField("A");
    }

    @Test
    public void validInput_onlyRange_valueMatch() {
        valueSet = new TreeSet<>();
        for(int i = fieldParser.getMin(); i <= fieldParser.getMax(); i++)
            valueSet.add(i);
        assertEquals(valueSet, fieldParser.parseField("0-59"));
    }

    @Test
    public void validInput_onlyStep_valueMatch() {
        valueSet = new TreeSet<>(Arrays.asList(1, 11, 21, 31, 41, 51));
        assertEquals(valueSet, fieldParser.parseField("1/10"));
    }

    @Test
    public void validInput_onlyStepWithAsterisk_valueMatch() {
        valueSet = new TreeSet<>(Arrays.asList(0, 20, 40));
        assertEquals(valueSet, fieldParser.parseField("*/20"));
    }

    @Test
    public void validInput_onlyList_valueMatch() {
        valueSet = new TreeSet<>(Arrays.asList(0, 4, 59));
        assertEquals(valueSet, fieldParser.parseField("0,4,59"));
    }

    @Test
    public void validInput_listRangeAndStep_valueMatch() {
        valueSet = new TreeSet<>(Arrays.asList(0, 4, 10, 11, 12, 20, 30, 40, 50, 51, 52, 53));
        assertEquals(valueSet, fieldParser.parseField("0,4,10-12,0/10,51-53,20/20"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_extraAsterisk_shouldThrowException() {
        fieldParser.parseField("*,4,10-12,*/10,51-53,20/20");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_withAsteriskInListField_shouldThrowException() {
        fieldParser.parseField("*,4,10-12,0/10,51-53,20/20");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidLessThanMinInRangeField_shouldThrowException() {
        fieldParser.parseField("-1-40");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidMoreThanMaxInRangeField_shouldThrowException() {
        fieldParser.parseField("0-60");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidLessThanMinInStepField_shouldThrowException() {
        fieldParser.parseField("-1/3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidMoreThanMaxInStepField_shouldThrowException() {
        fieldParser.parseField("60/1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_divideByZeroInStepField_shouldThrowException() {
        fieldParser.parseField("30/0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_negativeStepInStepField_shouldThrowException() {
        fieldParser.parseField("30/-1");
    }

    @Test
    public void validInput_moreThanMaxStepStepInStepField_shouldThrowException() {
        valueSet = new TreeSet<>(Collections.singletonList(5));
        assertEquals(valueSet, fieldParser.parseField("5/100"));
    }
}