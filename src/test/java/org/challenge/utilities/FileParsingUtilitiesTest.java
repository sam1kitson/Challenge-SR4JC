package org.challenge.utilities;

import jdk.jfr.Description;
import org.challenge.exceptions.FileParsingException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class FileParsingUtilitiesTest {

    private static final String TEST_FILE_CONTENTS_STRING = "A File Content with words 123";

    @Test
    @Description("validFilepath - Assert a valid file exists")
    public void validFilepathValidCase() {
        File mockedFile = Mockito.mock(File.class);
        Mockito.when(mockedFile.exists()).thenReturn(true);
        Mockito.when(mockedFile.isFile()).thenReturn(true);
        Mockito.when(mockedFile.canRead()).thenReturn(true);

        boolean result = FileParsingUtilities.validFilepath(mockedFile);

        Assert.assertTrue(result);
    }

    @Test
    @Description("validFilepath - Assert a file doesn't exist")
    public void validFilepathFileNotFound() {
        File mockedFile = Mockito.mock(File.class);
        Mockito.when(mockedFile.exists()).thenReturn(false);
        Mockito.when(mockedFile.isFile()).thenReturn(false);
        Mockito.when(mockedFile.canRead()).thenReturn(false);

        boolean result = FileParsingUtilities.validFilepath(mockedFile);

        Assert.assertFalse(result);
    }

    @Test
    @Description("validFilepath - Assert a file is actually a folder")
    public void validFilepathDirFound() {
        File mockedFile = Mockito.mock(File.class);
        Mockito.when(mockedFile.exists()).thenReturn(true);
        Mockito.when(mockedFile.isFile()).thenReturn(false);
        Mockito.when(mockedFile.canRead()).thenReturn(false);

        boolean result = FileParsingUtilities.validFilepath(mockedFile);

        Assert.assertFalse(result);
    }

    @Test
    @Description("validFilepath - Assert a file is detected as unreadable")
    public void validFilepathUnreadable() {
        File mockedFile = Mockito.mock(File.class);
        Mockito.when(mockedFile.exists()).thenReturn(true);
        Mockito.when(mockedFile.isFile()).thenReturn(true);
        Mockito.when(mockedFile.canRead()).thenReturn(false);

        boolean result = FileParsingUtilities.validFilepath(mockedFile);

        Assert.assertFalse(result);
    }

    @Test
    @Description("validFilepath - Null input")
    public void validFilepathWithNullInput() {
        Assert.assertThrows(NullPointerException.class,
                () -> FileParsingUtilities.validFilepath(null));
    }

    @Test
    @Description("readFileToString - Valid file read in")
    public void readFileToStringValidCase() throws FileParsingException {
        try (MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)) {

            Path mockedPath = Mockito.mock(Path.class);
            File mockedFile = Mockito.mock(File.class);
            Mockito.when(mockedFile.toPath()).thenReturn(mockedPath);

            filesMockedStatic.when(() ->
                            Files.readString(
                                    ArgumentMatchers.eq(mockedPath),
                                    ArgumentMatchers.eq(StandardCharsets.UTF_8)))
                    .thenReturn(TEST_FILE_CONTENTS_STRING);

            String result = FileParsingUtilities.readFileToString(mockedFile);

            Assert.assertEquals(TEST_FILE_CONTENTS_STRING, result);
        }
    }

    @Test
    @Description("readFileToString - Invalid Path")
    public void readFileToStringInvalidPath() {

        File mockedFile = Mockito.mock(File.class);
        Mockito.when(mockedFile.toPath()).thenThrow(new InvalidPathException("Invalid/path", "test exception"));

        Assert.assertThrows(IllegalArgumentException.class, () -> FileParsingUtilities.readFileToString(mockedFile));
    }

    @Test
    @Description("readFileToString - IO error occurs")
    public void readFileToStringIoError() {

        try (MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)) {

            Path mockedPath = Mockito.mock(Path.class);
            File mockedFile = Mockito.mock(File.class);
            Mockito.when(mockedFile.toPath()).thenReturn(mockedPath);

            filesMockedStatic.when(() ->
                            Files.readString(
                                    ArgumentMatchers.eq(mockedPath),
                                    ArgumentMatchers.eq(StandardCharsets.UTF_8)))
                    .thenThrow(new IOException("Test exception"));

            Assert.assertThrows(FileParsingException.class, () -> FileParsingUtilities.readFileToString(mockedFile));
        }
    }

    @Test
    @Description("readFileToString - Null input")
    public void readFileToStringWithNullInput() {
        Assert.assertThrows(NullPointerException.class,
                () -> FileParsingUtilities.readFileToString(null));
    }
}
