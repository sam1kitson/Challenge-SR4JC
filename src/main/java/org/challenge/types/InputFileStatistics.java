package org.challenge.types;

import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class InputFileStatistics {
    // Map of words within the input file, with a key of the word itself, and a value of the statistics associated
    private final Map<String, WordStatistics> wordStatisticsMap = new HashMap<>();

    /**
     * Function to count all the words in a collection
     *
     * @param wordCollection The collection of words to be counted
     */
    public void processWordCollection(List<String> wordCollection) {
        for (String word : wordCollection) {
            this.processWord(word);
        }
    }

    /**
     * Function to count a single word and apply it to the word statistics.
     *
     * @param word The word in the input file to be processed
     */
    public void processWord(String word) {
        if (!wordStatisticsMap.containsKey(word)) {
            wordStatisticsMap.put(word, new WordStatistics(word));
        }

        wordStatisticsMap.get(word).incrementWordCount();
    }

    /**
     * Function to return the word statistics of this input file.
     *
     * @return the word statistics of this input file (order is random)
     */
    public List<WordStatistics> fetchWordStatistics(Comparator<WordStatistics> comparator) {
        List<WordStatistics> words = new ArrayList<>(wordStatisticsMap.values());
        words.sort(comparator);
        return words;
    }
}
