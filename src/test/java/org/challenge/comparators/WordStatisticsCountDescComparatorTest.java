package org.challenge.comparators;

import jdk.jfr.Description;
import org.challenge.types.WordStatistics;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WordStatisticsCountDescComparatorTest {

    /* Standard set of word statistics */
    private static final WordStatistics statWith0 = new WordStatistics("Word0");

    private static final WordStatistics statWith1 = new WordStatistics("Word1");
    private static final WordStatistics statWith2 = new WordStatistics("Word2");

    static {
        statWith1.incrementWordCount();
    }

    static {
        statWith2.incrementWordCount();
        statWith2.incrementWordCount();
    }

    /* Test the comparator can sort a set correctly under different initials starting conditions */
    @Test
    @Description("Sort a set correct with the inverse initial conditions")
    public void sortNominalSet1() {
        List<WordStatistics> wordStatisticsList = new ArrayList<>();
        wordStatisticsList.add(statWith0);
        wordStatisticsList.add(statWith1);
        wordStatisticsList.add(statWith2);

        wordStatisticsList.sort(new WordStatisticsCountDescComparator());

        Assert.assertEquals(3, wordStatisticsList.size());
        Assert.assertEquals(wordStatisticsList.get(0), statWith2);
        Assert.assertEquals(wordStatisticsList.get(1), statWith1);
        Assert.assertEquals(wordStatisticsList.get(2), statWith0);
    }

    @Test
    @Description("Sort a set correct with random initial conditions")
    public void sortNominalSet2() {
        List<WordStatistics> wordStatisticsList = new ArrayList<>();
        wordStatisticsList.add(statWith1);
        wordStatisticsList.add(statWith0);
        wordStatisticsList.add(statWith2);

        wordStatisticsList.sort(new WordStatisticsCountDescComparator());

        Assert.assertEquals(3, wordStatisticsList.size());
        Assert.assertEquals(wordStatisticsList.get(0), statWith2);
        Assert.assertEquals(wordStatisticsList.get(1), statWith1);
        Assert.assertEquals(wordStatisticsList.get(2), statWith0);
    }

}
