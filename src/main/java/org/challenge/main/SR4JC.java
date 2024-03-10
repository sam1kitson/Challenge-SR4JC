package org.challenge.main;

import org.apache.commons.cli.*;
import org.challenge.comparators.WordStatisticsCountDescComparator;
import org.challenge.exceptions.FileParsingException;
import org.challenge.types.InputFileStatistics;
import org.challenge.types.WordStatistics;
import org.challenge.utilities.FileParsingUtilities;
import org.challenge.utilities.StringManipulationUtilities;

import java.util.List;

public class SR4JC {
    private static String inputFile = null;

    public static void main(String[] arguments) {
        try {
            // Process the command line arguments
            if (!parseArguments(arguments) || !validateArguments()) {
                System.exit(2);
            }

            // Input and process the file contents into words
            final String inputFileContents = FileParsingUtilities.readFileToString(inputFile);
            List<String> inputFileWords = StringManipulationUtilities.extractWordsWithinString(inputFileContents);

            // Process the words into statistics for the file
            InputFileStatistics inputFileStatistics = new InputFileStatistics();
            inputFileStatistics.processWordCollection(inputFileWords);

            List<WordStatistics> outputWords = inputFileStatistics.fetchWordStatistics(
                    new WordStatisticsCountDescComparator());

            // Output the words to the user
            for (WordStatistics word : outputWords) {
                System.out.println(word.toString());
            }

            System.exit(0);
        } catch (FileParsingException fileParsingException) {
            System.out.println("An error occurred while trying to parse the input file");
            System.out.println(fileParsingException.getMessage());

            System.exit(1);
        } catch (Exception exception) {
            System.out.println("An exception occurred unable to proceed");
            System.out.println(exception.getMessage());

            System.exit(1);
        }
    }

    private static boolean parseArguments(String[] arguments) {
        Option optionFilePath = Option.builder("f")
                .required(true)
                .desc("The input filepath to the file to be parsed and processed.")
                .longOpt("filepath")
                .hasArg()
                .build();

        Options options = new Options();
        options.addOption(optionFilePath);

        CommandLine commandLine;

        // Parse input parameters
        try {
            commandLine = (new DefaultParser()).parse(options, arguments);
        } catch (ParseException parseException) {
            System.out.println("Unable to parse input arguments");
            System.out.println(parseException.getMessage());
            return false;
        }

        // Fetch values
        if (commandLine.hasOption(optionFilePath)) {
            inputFile = commandLine.getOptionValue(optionFilePath);
        } else {
            return false;
        }

        return true;
    }

    private static boolean validateArguments() {
        if (inputFile == null || !FileParsingUtilities.validFilepath(inputFile)) {
            System.out.println("Invalid input file path");
            return false;
        }

        return true;
    }
}