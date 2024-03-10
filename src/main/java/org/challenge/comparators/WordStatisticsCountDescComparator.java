package org.challenge.comparators;

import org.challenge.types.WordStatistics;

import java.util.Comparator;

// Class to sort word statistics by word count, in descending order
public class WordStatisticsCountDescComparator implements Comparator<WordStatistics> {

    @Override
    public int compare(WordStatistics wordStatistics1, WordStatistics wordStatistics2) {
        return Integer.compare(wordStatistics2.getCount(), wordStatistics1.getCount());
    }
}
