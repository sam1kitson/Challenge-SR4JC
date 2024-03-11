package org.challenge.types;

import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InputFileStatisticsTest {

    @Test
    @Description("No words")
    public void noWords() {

        InputFileStatistics fileStatistics = new InputFileStatistics();

        List<WordStatistics> results = fileStatistics.fetchWordStatistics(new TestComparator());
        Assert.assertEquals(new ArrayList<>(), results);
    }

    @Test
    @Description("No words in empty array")
    public void noWordsInArray() {

        InputFileStatistics fileStatistics = new InputFileStatistics();
        fileStatistics.processWordCollection(new ArrayList<>());

        List<WordStatistics> results = fileStatistics.fetchWordStatistics(new TestComparator());
        Assert.assertEquals(new ArrayList<>(), results);
    }

    @Test
    @Description("Multiple of the same word")
    public void multipleSameWord() {

        InputFileStatistics fileStatistics = new InputFileStatistics();
        fileStatistics.processWord("TEST");
        fileStatistics.processWord("TEST");

        List<WordStatistics> results = fileStatistics.fetchWordStatistics(new TestComparator());
        Assert.assertEquals(1, results.size());
        Assert.assertEquals("TEST", results.get(0).getWord());
        Assert.assertEquals(2, results.get(0).getCount());
    }

    @Test
    @Description("Multiple of the different word")
    public void multipleDifferentWord() {

        List<String> input = new ArrayList<>();
        input.add("TEST1");
        input.add("TEST2");

        InputFileStatistics fileStatistics = new InputFileStatistics();
        fileStatistics.processWordCollection(input);

        List<WordStatistics> results = fileStatistics.fetchWordStatistics(new TestComparator());
        Assert.assertEquals(2, results.size());
        Assert.assertEquals("TEST1", results.get(0).getWord());
        Assert.assertEquals(1, results.get(0).getCount());
        Assert.assertEquals("TEST2", results.get(1).getWord());
        Assert.assertEquals(1, results.get(1).getCount());
    }

    @Test
    @Description("Test sorter is being used")
    public void testSorter() {

        List<String> input = new ArrayList<>();
        input.add("TEST2"); // This should be placed at the bottom of the list when sorted
        input.add("TEST1");

        InputFileStatistics fileStatistics = new InputFileStatistics();
        fileStatistics.processWordCollection(input);

        List<WordStatistics> results = fileStatistics.fetchWordStatistics(new TestComparator());
        Assert.assertEquals(2, results.size());
        Assert.assertEquals("TEST1", results.get(0).getWord());
        Assert.assertEquals(1, results.get(0).getCount());
        Assert.assertEquals("TEST2", results.get(1).getWord());
        Assert.assertEquals(1, results.get(1).getCount());
    }
}

/* Generic comparator to test sorting functionality */
class TestComparator implements Comparator<WordStatistics> {

    @Override
    public int compare(WordStatistics o1, WordStatistics o2) {
        return o1.getWord().compareTo(o2.getWord());
    }
}