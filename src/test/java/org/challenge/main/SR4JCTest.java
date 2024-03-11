package org.challenge.main;

import jdk.jfr.Description;
import org.challenge.exceptions.FileParsingException;
import org.challenge.utilities.FileParsingUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.logging.Logger;

public class SR4JCTest {

    @Test
    @Description("ProgramWrapper - Test Nominal")
    public void programWrapperTestNominal() {
        try (MockedStatic<SR4JC> sr4JCMockedStatic = Mockito.mockStatic(SR4JC.class)) {
            String[] testInput = new String[]{"test", "param"};

            // Exclude the function being tested
            sr4JCMockedStatic.when(() -> SR4JC.main(ArgumentMatchers.any())).thenAnswer(Answers.CALLS_REAL_METHODS);
            sr4JCMockedStatic.when(() -> SR4JC.runScript(ArgumentMatchers.eq(testInput), ArgumentMatchers.any()))
                    .thenReturn(0);
            sr4JCMockedStatic.when(() -> SR4JC.systemExit(ArgumentMatchers.anyInt())).thenAnswer(Answers.RETURNS_DEFAULTS);

            SR4JC.main(testInput);

            sr4JCMockedStatic.verify(() -> SR4JC.runScript(ArgumentMatchers.eq(testInput), ArgumentMatchers.any()),
                    Mockito.times(1));

            // Verify only exits on 0
            sr4JCMockedStatic.verify(() -> SR4JC.systemExit(ArgumentMatchers.eq(0)), Mockito.times(1));
            sr4JCMockedStatic.verify(() -> SR4JC.systemExit(ArgumentMatchers.anyInt()), Mockito.times(1));
        }
    }

    @Test
    @Description("ProgramWrapper - Exception thrown")
    public void programWrapperExcpetionCatchingTest() {
        try (MockedStatic<SR4JC> sr4JCMockedStatic = Mockito.mockStatic(SR4JC.class)) {
            String[] testInput = new String[]{"test", "param"};

            // Exclude the function being tested
            sr4JCMockedStatic.when(() -> SR4JC.main(ArgumentMatchers.any())).thenAnswer(Answers.CALLS_REAL_METHODS);
            sr4JCMockedStatic.when(() -> SR4JC.runScript(ArgumentMatchers.eq(testInput), ArgumentMatchers.any()))
                    .thenThrow(new RuntimeException("Test Exception"));
            sr4JCMockedStatic.when(() -> SR4JC.systemExit(ArgumentMatchers.anyInt())).thenAnswer(Answers.RETURNS_DEFAULTS);

            SR4JC.main(testInput);

            sr4JCMockedStatic.verify(() -> SR4JC.runScript(ArgumentMatchers.eq(testInput), ArgumentMatchers.any()), Mockito.times(1));

            // Verify only exits on 2
            sr4JCMockedStatic.verify(() -> SR4JC.systemExit(ArgumentMatchers.eq(1)), Mockito.times(1));
            sr4JCMockedStatic.verify(() -> SR4JC.systemExit(ArgumentMatchers.anyInt()), Mockito.times(1));
        }
    }

    @Test
    @Description("RunScript - No Parameters")
    public void runScriptNoParameters() throws FileParsingException {
        Logger mockedLogger = Mockito.mock(Logger.class);

        int result = SR4JC.runScript(new String[]{}, mockedLogger);

        Assert.assertEquals(2, result);
        Mockito.verify(mockedLogger).severe(ArgumentMatchers.eq("Unable to parse input arguments"));
        Mockito.verify(mockedLogger).severe(ArgumentMatchers.eq("Missing required option: f"));

    }

    @Test
    @Description("RunScript - Invalid filepath")
    public void runScripInvalidFilepath() {
        Logger mockedLogger = Mockito.mock(Logger.class);

        try (MockedStatic<FileParsingUtilities> fileParsingMockedStatic
                     = Mockito.mockStatic(FileParsingUtilities.class)) {
            fileParsingMockedStatic.when(() -> FileParsingUtilities.validFilepath(ArgumentMatchers.any()))
                    .thenReturn(false);

            int result = SR4JC.runScript(new String[]{"-f", "invalid/file/path"}, mockedLogger);

            Assert.assertEquals(2, result);
            Mockito.verify(mockedLogger).severe(ArgumentMatchers.eq("Invalid input file path"));
        }
    }

    @Test
    @Description("RunScript - File parsing issue")
    public void runScripFileParsingIssue() {
        Logger mockedLogger = Mockito.mock(Logger.class);

        try (MockedStatic<FileParsingUtilities> fileParsingMockedStatic
                     = Mockito.mockStatic(FileParsingUtilities.class)) {
            fileParsingMockedStatic.when(() -> FileParsingUtilities.validFilepath(ArgumentMatchers.any()))
                    .thenReturn(true);
            fileParsingMockedStatic.when(() -> FileParsingUtilities.readFileToString(ArgumentMatchers.any()))
                    .thenThrow(new FileParsingException("TEST EXCEPTION"));

            int result = SR4JC.runScript(new String[]{"-f", "valid/file/path"}, mockedLogger);

            Assert.assertEquals(2, result);
            Mockito.verify(mockedLogger).severe(ArgumentMatchers.eq("Unable to parse the file, error occurred"));
            Mockito.verify(mockedLogger).severe(ArgumentMatchers.eq("TEST EXCEPTION"));
        }
    }

    @Test
    @Description("RunScript - Valid path with test file")
    public void runScripValidPathWithFile() {
        Logger mockedLogger = Mockito.mock(Logger.class);

        try (MockedStatic<FileParsingUtilities> fileParsingMockedStatic
                     = Mockito.mockStatic(FileParsingUtilities.class)) {
            try (MockedStatic<SR4JC> sr4JCMockedStatic = Mockito.mockStatic(SR4JC.class)) {
                fileParsingMockedStatic.when(() -> FileParsingUtilities.validFilepath(ArgumentMatchers.any()))
                        .thenReturn(true);
                fileParsingMockedStatic.when(() -> FileParsingUtilities.readFileToString(ArgumentMatchers.any()))
                        .thenReturn("a test file content");

                // Mock system exit and print string to be verified
                sr4JCMockedStatic.when(() -> SR4JC.runScript(ArgumentMatchers.any(), ArgumentMatchers.any())).thenAnswer(Answers.CALLS_REAL_METHODS);
                sr4JCMockedStatic.when(() -> SR4JC.systemExit(ArgumentMatchers.anyInt())).thenAnswer(Answers.RETURNS_DEFAULTS);
                sr4JCMockedStatic.when(() -> SR4JC.printlnString(ArgumentMatchers.any())).thenAnswer(Answers.RETURNS_DEFAULTS);

                int result = SR4JC.runScript(new String[]{"-f", "valid/file/path"}, mockedLogger);

                Assert.assertEquals(0, result);
                sr4JCMockedStatic.verify(() -> SR4JC.printlnString(ArgumentMatchers.eq("a: 1")), Mockito.times(1));
                sr4JCMockedStatic.verify(() -> SR4JC.printlnString(ArgumentMatchers.eq("test: 1")), Mockito.times(1));
                sr4JCMockedStatic.verify(() -> SR4JC.printlnString(ArgumentMatchers.eq("file: 1")), Mockito.times(1));
                sr4JCMockedStatic.verify(() -> SR4JC.printlnString(ArgumentMatchers.eq("content: 1")), Mockito.times(1));
            }
        }
    }
}
