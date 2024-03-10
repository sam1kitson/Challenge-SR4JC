package org.challenge.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// Class to
@Getter
@RequiredArgsConstructor
public class WordStatistics {

    // The word which this object stores statistics on
    private final String word;

    // Indication of the number of occurrences within a file
    private int count = 0;

    /**
     * Increase the number of words seen
     */
    public void incrementWordCount() {
        count++;
    }

    public String toString() {
        return String.format("%s: %d", this.word, this.count);
    }
}
