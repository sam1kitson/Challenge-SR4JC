package org.challenge.utilities;

import java.util.ArrayList;
import java.util.List;

public class StringManipulationUtilities {

    // Additional space characters which are allowed to separate words
    private static final Character[] ADDITIONAL_SPACE_CHARACTERS = new Character[]{'\t', '\n', '\r'};
    // Default case sensitivity to use when preforming utilities
    private static final boolean DEFAULT_CASE_SENSITIVITY = false;

    /**
     * Function to extract all the words in a string.
     * Assumes a case sensitivity set by DEFAULT_CASE_SENSITIVITY, and allows characters to be separated by a ' ' or
     * anything in the ADDITIONAL_SPACE_CHARACTERS array.
     *
     * @param inputString The input string to extract the words from
     * @return The words from the input string stored in a list
     */
    public static List<String> extractWordsWithinString(final String inputString) {

        String processedInput = DEFAULT_CASE_SENSITIVITY ? inputString : inputString.toLowerCase();

        for (Character spaceCharacter : ADDITIONAL_SPACE_CHARACTERS) {
            processedInput = processedInput.replace(spaceCharacter, ' ');
        }

        String[] separatedWords = processedInput.split(" ");
        List<String> validatedWords = new ArrayList<>();

        for (String word : separatedWords) {
            if (!word.isEmpty()) {
                validatedWords.add(word);
            }
        }

        return validatedWords;
    }
}
