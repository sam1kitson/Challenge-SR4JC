# Challenge-SR4JC

A solution to the word count challenge (SR4JC), where an input text file is specified in the parameters, and the solution should read the word contents and produce a word count for each word in a count descending order.
This solution has been written in Java, using maven to handle libraries and packaging.

To build and run the solution solution follow the following steps:
1. Install the required prerequisites: [Chapter - Prerequisites](#prerequisites)
2. Run the unit tests, and compile the jar: [Chapter - Building The Project](#building-the-project)
3. Running the project: [Chapter - Running The Project](#running-the-project)

Alternatively, to use a pre-compiled version follow the following steps:
1. Install the OpenJDK 21
2. Download the SR4JC-1.0-SNAPSHOT.jar from https://github.com/sam1kitson/Challenge-SR4JC/releases/tag/1.0
3. Run the jar using the following command: `java -jar [INSERT_PATH_TO_DOWNLOADED_JAR] -f "mockdata/textfile.txt"`

## Prerequisites

- <a href=https://jdk.java.net/21/>Java JDK</a> 
  - Requires 21+
  - Certified on OpenJDK 21
- <a href=https://maven.apache.org/download.cgi>Maven</a>
  - Requires 3.9.0+
  - Certified on version 3.9.6

## Building The Project

To build the project and generate a runnable jar, run the following command from the project root:

`mvn clean install`

This command will also run all unit tests before building the jar file.

## Running The Project
To run the project once the project has been built, run the following command from the project root:

`java -jar target/SR4JC-1.0-SNAPSHOT.jar -f "mockdata/textfile.txt"`

The argument after the -f flag (in this case mockdata/textfile.txt) can be replaced with an absolute or relative path 
to a text file as needed.

## End-To-End Tests
As this is a small scale project the end-to-end tests are to be run manually using the following commands from the project root:

### Test 1 - Valid case
`java -jar target/SR4JC-1.0-SNAPSHOT.jar -f "mockdata/integration_test.txt"`

Expected output:
```
clock: 5
partnership: 4
seem: 3
threaten: 2
wine: 1
```

### Test 2 - Invalid file
`java -jar target/SR4JC-1.0-SNAPSHOT.jar -f "mockdata/not_a_file.txt"`

Expected output:
```
SEVERE: Invalid input file path
```


## Assumptions

- File encoding of the input file is UTF-8
- The definition of a word consists of the following:
  - A collection of one or more consecutive alphanumeric characters
  - Separated by any of the following space characters:
    - A space character (' ')
    - A tab character
    - A newline character (\n and/or \r)
  - Any character which is not an alphanumeric or space character can be ignored
- Words are considered the same, regardless of the case of its characters
