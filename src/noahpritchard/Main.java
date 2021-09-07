package noahpritchard;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/*
Assume that user is using banking application on phone to make some transaction
User has 2 options to enter numbers
- Phone keypad where output is 2, 23 etc
- Voice recognition where output is two, twenty three etc
Write function that returns sum of two such user-generated numbers.
Each time a function is called it should write (log) to a file 3 values, both numbers and sum, separated by comma.
Feel free to make assumptions like number range 1 to 10, English language only etc but prepare to discuss different use cases.
 */

/*
Assumptions:
    Logged entries are to be appended to log file
    Values are all whole dollar/currency amounts in English (but other languages will be supported in the future)
    Values and their sums can fit within an int (4-bytes) (no overflow protection)
    Phone keypad produces numeric characters only and will always be valid numbers (no commas, hyphens, etc.)
    Voice recognition produces english words in full spelled out form
        "four hundred twenty two" is allowed
        "four hundred two" is allowed - omitting absent "tens" number
        "four hundred and two" is allowed - including "and"
        "one hundred two" is allowed
        "twenty five hundred" is allowed - informal thousands
        "twenty one thousand eleven hundred" is not allowed - combining informal thousands with existing thousands
        "one thousand thousand" is not allowed - stacking multipliers
        "one hundred thousand one million" is not allowed - mis-ordered number
        "a hundred two" is not allowed - "a" instead of "one"
        "one two seven eight" is not allowed - spelling out a number one digit at a time
 */

public class Main {

    public static void main(String[] args) {
        final Map<String, SpelledOutNumberParser> parsers = Map.of(Locale.ENGLISH.getLanguage(), new EnglishNumberParser());

        Scanner scanner = new Scanner(System.in);

        //We could get locale info from system or user selection
        SpelledOutNumberParser parser = parsers.get(Locale.US.getLanguage());

        if (parser == null) {
            printLine("Warning, spelled out numbers cannot be converted for your language setting.");
            printBlankLine();
        }

        boolean userQuit = false;

        while (!userQuit) { //Loop program for demonstration purposes
            try {
                int value1 = getInputUntilValid(scanner, parser);
                int value2 = getInputUntilValid(scanner, parser);

                try {
                    int sum = sumAndLog(value1, value2);
                    printLine(String.format("Sum is %d", sum));

                } catch (IOException loggingException) {
                    //printLine(loggingException.getMessage());
                    printLine("Logging failed, please try again.");
                }
                printBlankLine();

            } catch (IOException quitException) {
                printBlankLine();
                printLine(quitException.getMessage());
                userQuit = true;
            }
        }
    }

    //Returns valid input value, throws exception (only) if user quit
    static int getInputUntilValid(Scanner scanner, SpelledOutNumberParser converter) throws IOException {
        Integer value = null;

        String quitString = "quit";
        do {
            printLine(String.format("Please enter an amount to add or \"%s\" to quit", quitString));

            String input;
            input = scanner.nextLine();
            if (input.equalsIgnoreCase(quitString)) {
                throw new IOException("User quit.");
            }
            else {
                try {
                    value = Integer.parseInt(input);
                } catch (NumberFormatException exception) {
                    if (converter != null) {
                        value = converter.parseInt(input);
                    }
                }

                if (value != null) {
                    printLine(String.format("Received value: %d", value));
                }
                else {
                    printLine("Invalid input, please try again.");
                }
                printBlankLine();
            }
        } while (value == null);

        return value;
    }

    //The function specified for the task
    static int sumAndLog(int value1, int value2) throws IOException {
        int sum = value1 + value2;

        String outputFileName = "log.txt";
        String spacer = ",";

        try (FileWriter writer = new FileWriter(outputFileName, true)) {
            writer.write(String.format("%d%s%d%s%d", value1, spacer, value2, spacer, sum));
            writer.write(System.lineSeparator());
        }

        return sum;
    }


    static void printLine(String string) {
        System.out.println(string);
    }

    static void printBlankLine() {
        printLine("");
    }
}
