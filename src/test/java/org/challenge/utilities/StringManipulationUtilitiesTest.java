package org.challenge.utilities;

import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StringManipulationUtilitiesTest {

    @Test
    @Description("extractWordsWithinString - Single word")
    public void extractWordsWithinStringSingleWord() {
        final String testInput = "A";
        final List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("a");

        List<String> results = StringManipulationUtilities.extractWordsWithinString(testInput);

        Assert.assertEquals(expectedOutput, results);
    }

    @Test
    @Description("extractWordsWithinString - Standard set of words")
    public void extractWordsWithinStringStandardWords() {
        final String testInput = "A simple word pattern";
        final List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("a");
        expectedOutput.add("simple");
        expectedOutput.add("word");
        expectedOutput.add("pattern");

        List<String> results = StringManipulationUtilities.extractWordsWithinString(testInput);

        Assert.assertEquals(expectedOutput, results);
    }

    @Test
    @Description("extractWordsWithinString - Empty input string")
    public void extractWordsWithinStringEmptyInput() {
        final String testInput = "";
        final List<String> expectedOutput = new ArrayList<>();

        List<String> results = StringManipulationUtilities.extractWordsWithinString(testInput);

        Assert.assertEquals(expectedOutput, results);
    }

    @Test
    @Description("extractWordsWithinString - Spaces at start and end of input")
    public void extractWordsWithinStringWithStartingAndEndingSpaces() {
        final String testInput = " A simple word pattern ";
        final List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("a");
        expectedOutput.add("simple");
        expectedOutput.add("word");
        expectedOutput.add("pattern");

        List<String> results = StringManipulationUtilities.extractWordsWithinString(testInput);

        Assert.assertEquals(expectedOutput, results);
    }

    @Test
    @Description("extractWordsWithinString - Multiple spaces")
    public void extractWordsWithinStringWithMultipleSpaces() {
        final String testInput = "A simple     word pattern";
        final List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("a");
        expectedOutput.add("simple");
        expectedOutput.add("word");
        expectedOutput.add("pattern");

        List<String> results = StringManipulationUtilities.extractWordsWithinString(testInput);

        Assert.assertEquals(expectedOutput, results);
    }

    @Test
    @Description("extractWordsWithinString - Non standard spaces")
    public void extractWordsWithinStringWithNonStandardSpaces() {
        final String testInput = "A\tsimple\tword\npattern";
        final List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("a");
        expectedOutput.add("simple");
        expectedOutput.add("word");
        expectedOutput.add("pattern");

        List<String> results = StringManipulationUtilities.extractWordsWithinString(testInput);

        Assert.assertEquals(expectedOutput, results);
    }

    @Test
    @Description("extractWordsWithinString - Mixing space types")
    public void extractWordsWithinStringWithMixOfSpaces() {
        final String testInput = "A simple\t \nword pattern";
        final List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("a");
        expectedOutput.add("simple");
        expectedOutput.add("word");
        expectedOutput.add("pattern");

        List<String> results = StringManipulationUtilities.extractWordsWithinString(testInput);

        Assert.assertEquals(expectedOutput, results);
    }

    @Test
    @Description("extractWordsWithinString - Using numbers")
    public void extractWordsWithinStringWithNumbers() {
        final String testInput = "1 2 3 44";
        final List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("1");
        expectedOutput.add("2");
        expectedOutput.add("3");
        expectedOutput.add("44");

        List<String> results = StringManipulationUtilities.extractWordsWithinString(testInput);

        Assert.assertEquals(expectedOutput, results);
    }

    @Test
    @Description("extractWordsWithinString - Non alphanumeric only")
    public void extractWordsWithinStringWithNonAlphanumericOnly() {
        final String testInput = "! ? // *";
        final List<String> expectedOutput = new ArrayList<>();

        List<String> results = StringManipulationUtilities.extractWordsWithinString(testInput);

        Assert.assertEquals(expectedOutput, results);
    }

    @Test
    @Description("extractWordsWithinString - Some non alphanumeric")
    public void extractWordsWithinStringWithNonAlphanumeric() {
        final String testInput = "Hello! a question?";
        final List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("hello");
        expectedOutput.add("a");
        expectedOutput.add("question");

        List<String> results = StringManipulationUtilities.extractWordsWithinString(testInput);

        Assert.assertEquals(expectedOutput, results);
    }

    @Test
    @Description("extractWordsWithinString - Null input")
    public void extractWordsWithinStringWithNullInput() {

        Assert.assertThrows(NullPointerException.class,
                () -> StringManipulationUtilities.extractWordsWithinString(null));
    }
}
