package com.healthycoderapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @Test
    void should_ReturnTrue_When_DietIsRecommended() {
        //given
        double weight = 89.0;
        double height = 1.72;
        //when
        boolean result = BMICalculator.isDietRecommended(weight, height);

        //then
        assertTrue(result);
    }

    @Test
    void should_ReturnFalse_When_DietIsRecommended() {
        //given
        double weight = 50.0;
        double height = 1.92;

        //when
        boolean result = BMICalculator.isDietRecommended(weight, height);

        //then
        assertFalse(result);
    }

    @Test
    void should_ThrowArithmeticException_When_HeightIsZero() {
        //given
        double weight = 50.0;
        double height = 0.0;

        //when
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

        //then
        assertThrows(ArithmeticException.class, executable);
    }

}