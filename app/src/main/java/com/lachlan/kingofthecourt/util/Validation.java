package com.lachlan.kingofthecourt.util;

/**
 * Class which performs validation of various conditions.
 *
 * @author Lachlan Collins
 * @version 20 February 2021
 */
public class Validation {
    /**
     * Default constructor to initialise object of the Validation class.
     */
    public Validation() {
    }

    /**
     * Method that determines whether an integer is between the specified minimum and maximum values (exclusive).
     *
     * @param input An integer representing the input that is to be tested.
     * @param min An integer representing the minimum bounds of the test.
     * @param max An integer representing the maximum bounds of the test.
     * @return True if the input integer is between the minimum and maximum values, False otherwise.
     */
    public boolean between(int input, int min, int max) {
        return min < input && input < max;
    }

    /**
     * Method that determines whether a String is within the specified minimum and maximum lengths (inclusive).
     *
     * @param input A String representing the input that is to be tested.
     * @param min An integer representing the minimum length of the input allowed.
     * @param max An integer representing the maximum length of the input allowed.
     * @return True if the input String length is within the range of the two values, False otherwise.
     */
    public boolean inRange(String input, int min, int max) {
        return input.trim().length() >= min && input.trim().length() <= max;
    }

    /**
     * Method that determines whether a String is blank and returns a boolean condition.
     *
     * @param input A String representing the input that is to be tested.
     * @return True if the String is blank, False otherwise.
     */
    public boolean isBlank(String input) {
        return input.trim().length() == 0;
    }
}
