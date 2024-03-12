package org.challenge.utilities;

import org.challenge.exceptions.FileParsingException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class FileParsingUtilities {

    // The encoding which is used for any encoding/decoding of files on disk
    private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

    /**
     * Validates an input filepath, checking that the outlined filepath exists as a file on the filesystem
     *
     * @param fileToBeValidated The file to be checked against the filesystem
     * @return The validity status of the filepath (True indicates the file exists in the filesystem)
     */
    public static boolean validFilepath(final File fileToBeValidated) {
        return fileToBeValidated.exists() && fileToBeValidated.isFile() && fileToBeValidated.canRead();
    }

    /**
     * Reads a file denoted by a filepath from the filesystem into a string.
     * The file is assumed to be the default encoding of UTF-8
     * The filepath is assumed to be pre-validated.
     *
     * @param file The file to be read from the filesystem
     * @return The contents of the file decoded into a string
     * @throws IllegalArgumentException When an invalid filepath is passed as a parameter
     * @throws FileParsingException     When unable to read in the file (due to IO error, or out of memory error,
     *                                  or security error)
     */
    public static String readFileToString(final File file) throws FileParsingException {
        try {
            final Path filepathToBeRead = file.toPath();

            return Files.readString(filepathToBeRead, DEFAULT_ENCODING);
        } catch (InvalidPathException invalidPathException) {
            throw new IllegalArgumentException("Invalid path given as parameter", invalidPathException);
        } catch (IOException | OutOfMemoryError | SecurityException exception) {
            throw new FileParsingException("Unable to read file contents due to exception", exception);
        }
    }
}
