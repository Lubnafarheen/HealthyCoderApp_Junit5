package com.healthycoderapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @Test
    void testDietIsRecommended_weightMoreThan55KgAndHeightBelow6feet_shouldReturnTrue() {
        //given
        double weight = 89.0;
        double height = 1.72;
        //when
        boolean result = BMICalculator.isDietRecommended(weight, height);

        //then
        assertTrue(result);
    }

    @Test
    void testDietIsRecommended_weightEquals50KgAndHeightBelow6feet_shouldReturnTrue() {
        //given
        double weight = 50.0;
        double height = 1.92;

        //when
        boolean result = BMICalculator.isDietRecommended(weight, height);

        //then
        assertFalse(result);
    }

    @Test
    void testIfHeightIsZero_ShouldThrowArithmeticException() {
        //given
        double weight = 50.0;
        double height = 0.0;

        //when
        // Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

        //then
        assertThrows(ArithmeticException.class, () -> BMICalculator.isDietRecommended(weight, height));
    }

    @Test
    void testFindCoderWithWorstBMI_weightMoreThan55KgAndHeightBelow6feet_ShouldReturnCoder() {
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 64.0));
        coders.add(new Coder(1.85, 69.0));
        coders.add(new Coder(1.82, 98.0));

        Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
        assertAll(
                () -> assertEquals(1.82, coderWithWorstBMI.getHeight()),
                () -> assertEquals(98.0, coderWithWorstBMI.getWeight())
        );
    }

    @Test
    void testFindCoderWithWorstBMI_WhenCoderListIsEmpty_ShouldReturnNull() {
        List<Coder> coders = new ArrayList<>();

        Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        assertNull(coderWithWorstBMI);
    }

    @Test
    void testGetBMIScores_ShouldReturnCorrectArrayOfBMIScores() {
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 64.0));
        coders.add(new Coder(1.85, 69.0));
        coders.add(new Coder(1.82, 98.0));
        double[] expected = { 18.52, 19.32, 20.16, 29.59 };
        assertArrayEquals(expected, BMICalculator.getBMIScores(coders));
    }
}

