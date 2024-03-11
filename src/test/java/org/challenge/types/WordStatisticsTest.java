package org.challenge.types;

import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;

public class WordStatisticsTest {

    @Test
    @Description("No counts found")
    public void noCountsFound() {

        WordStatistics word = new WordStatistics("TEST");

        Assert.assertEquals(word.getWord(), "TEST");
        Assert.assertEquals(word.toString(), "TEST: 0");
    }

    @Test
    @Description("1 count")
    public void singleCount() {

        WordStatistics word = new WordStatistics("TEST");
        word.incrementWordCount();

        Assert.assertEquals(word.getWord(), "TEST");
        Assert.assertEquals(word.toString(), "TEST: 1");
    }

    @Test
    @Description("2 count")
    public void multipleCount() {

        WordStatistics word = new WordStatistics("TEST");
        word.incrementWordCount();
        word.incrementWordCount();

        Assert.assertEquals(word.getWord(), "TEST");
        Assert.assertEquals(word.toString(), "TEST: 2");
    }
}
