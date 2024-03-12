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
     * anything in the ADDITIONAL_SPACE_CHARACTERS array. Non-alphanumeric characters (excl. spaces and
     * ADDITIONAL_SPACE_CHARACTERS) are removed.
     *
     * @param inputString The input string to extract the words from
     * @return The words from the input string stored in a list
     */
    public static List<String> extractWordsWithinString(final String inputString) {

        // Set the correct case if required
        String processedInput = DEFAULT_CASE_SENSITIVITY ? inputString : inputString.toLowerCase();

        // Standardise the space between words
        for (final Character spaceCharacter : ADDITIONAL_SPACE_CHARACTERS) {
            processedInput = processedInput.replace(spaceCharacter, ' ');
        }

        // Remove non-alphanumeric characters (excluding spaces)
        processedInput = processedInput.replaceAll("[^a-zA-Z0-9 ]", "");

        // Separate out each word
        final String[] separatedWords = processedInput.split(" ");
        final List<String> validatedWords = new ArrayList<>();

        for (final String word : separatedWords) {
            if (!word.isEmpty()) {
                validatedWords.add(word);
            }
        }

        return validatedWords;
    }
}
