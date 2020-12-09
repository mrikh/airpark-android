package com.example.airpark;

import com.example.airpark.utils.InputValidator;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class InputValidatorTest {

    private InputValidator validator;

    @BeforeEach
    void setUp(){
        validator = new InputValidator();
    }

    @AfterEach
    void tearDown(){
        validator = null;
    }

    @ParameterizedTest
    @CsvSource({" , false", "'', false", "' ', false", "mayank, false", "mayankrikh@, false", "@gmail.com, false" , "mayankrikh@gmail, false", "mayankrikh@gmail.com, true", "mayankrikh@gmail.co.in, true"})
    public void emailValidationTest(String email, boolean result) {
        assertEquals(result, validator.isValidEmail(email));
    }


}