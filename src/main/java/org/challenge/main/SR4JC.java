package org.challenge.main;

import org.apache.commons.cli.*;
import org.challenge.comparators.WordStatisticsCountDescComparator;
import org.challenge.exceptions.FileParsingException;
import org.challenge.types.InputFileStatistics;
import org.challenge.types.WordStatistics;
import org.challenge.utilities.FileParsingUtilities;
import org.challenge.utilities.StringManipulationUtilities;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SR4JC {

    private final static Option OPTION_FILEPATH = Option.builder("f")
            .required(true)
            .desc("The input filepath to the file to be parsed and processed.")
            .longOpt("filepath")
            .hasArg()
            .build();

    private static final Options OPTIONS = new Options();
    private static final Level LOGGING_LEVEL = Level.SEVERE;

    static {
        OPTIONS.addOption(OPTION_FILEPATH);
    }

    /**
     * Entrypoint function, acts as a wrapper to the program as a whole to log top level exceptions.
     * This function will return a status code, and log any issues accordingly
     * Status code:
     * - 0: Success
     * - 1: Error (General)
     * - 2: Error (Input parameters)
     *
     * @param arguments The input parameters to be passed in
     */
    public static void main(String[] arguments) {
        final Logger logger = Logger.getLogger("org.challenge.SR4JC");
        logger.setLevel(LOGGING_LEVEL);

        try {
            systemExit(runScript(arguments, logger));
        } catch (Exception exception) {
            logger.severe("An exception occurred unable to proceed");
            logger.severe(exception.getMessage());

            systemExit(1);
        }
    }

    /**
     * Function to run the decoding and processing of the text file.
     * @param arguments The arguments passed into the program
     * @param logger The logger to use to report issues
     * @return The status code to be returned
     */
    public static int runScript(final String[] arguments, final Logger logger) {
        CommandLine commandLine;
        File inputFile;

        // Parse input parameters
        try {
            commandLine = (new DefaultParser()).parse(OPTIONS, arguments);
        } catch (ParseException parseException) {
            logger.severe("Unable to parse input arguments");
            logger.severe(parseException.getMessage());
            return 2;
        }

        // Fetch values
        inputFile = new File(commandLine.getOptionValue(OPTION_FILEPATH));

        // Validate input parameters
        if (!FileParsingUtilities.validFilepath(inputFile)) {
            logger.severe("Invalid input file path");
            return 2;
        }

        logger.info(String.format("Parsed input parameter: inputFile - \"%s\"", inputFile.getPath()));

        // Input and process the file contents into words
        final String inputFileContents;
        try {
            inputFileContents = FileParsingUtilities.readFileToString(inputFile);
        } catch (FileParsingException fileParsingException) {
            logger.severe("Unable to parse the file, error occurred");
            logger.severe(fileParsingException.getMessage());
            return 2;
        }
        List<String> inputFileWords = StringManipulationUtilities.extractWordsWithinString(inputFileContents);

        logger.info("Extracted word content of file");

        // Process the words into statistics for the file
        InputFileStatistics inputFileStatistics = new InputFileStatistics();
        inputFileStatistics.processWordCollection(inputFileWords);

        logger.info("Words counted and processed");

        List<WordStatistics> outputWords = inputFileStatistics.fetchWordStatistics(
                new WordStatisticsCountDescComparator());

        // Output the words to the user
        for (final WordStatistics word : outputWords) {
            printlnString(word.toString());
        }

        return 0;
    }

    /**
     * Wrapped call to the system exit command
     *
     * @param status The status code to preform the exit
     */
    public static void systemExit(final int status) {
        System.exit(status);
    }

    /**
     * Wrapped call to println command
     *
     * @param message The message to be printed
     */
    public static void printlnString(final String message) {
        System.out.println(message);
    }
}