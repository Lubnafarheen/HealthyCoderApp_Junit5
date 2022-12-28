package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BMICalculatorTest {

    private String environment = "dev";

    @BeforeAll
    static void beforeAll() {
        System.out.println("before");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after");
    }

    @Nested
    class IsDietRecommendedTest {

        @ParameterizedTest(name = "weight={0}, height={0}")
        //@ValueSource(doubles = { 89.9, 110.0})
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void testDietIsRecommended_weightMoreThan70KgAndHeightBelow6feet_shouldReturnTrue(double coderWeight, double coderHeight) {
            //given
            double weight = coderWeight;
            double height = coderHeight;
            //when
            boolean result = BMICalculator.isDietRecommended(weight, height);

            //then
            assertTrue(result);
        }

        @ParameterizedTest(name = "weight={0}, height={0}")
        @CsvSource(value = { "46.0, 1.74", "55.8, 1.72" })
        void testDietIsRecommended_weightEquals70KgOrLessAndHeightBelow6feet_shouldReturnFalse(double coderWeight, double coderHeight) {
            //given
            double weight = coderWeight;
            double height = coderHeight;

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


    }

    @Nested
    class FindCoderWithWorstBMITest {

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
        void testFindCoderWithWorstBMI_WhenCoderListHas10000Elements_ShouldReturnCoderIn100Ms() {

            assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
            List<Coder> coders = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                coders.add(new Coder(1.0 + i, 10.0 + i));
            }
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

            assertTimeout(Duration.ofMillis(100), executable);
        }

    }

    @Nested
    class GetBMIScoresTest {
        @Test
        @DisplayName("BMI Scores")
        @DisabledOnOs(OS.WINDOWS)
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


}

