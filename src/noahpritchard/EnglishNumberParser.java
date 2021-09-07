/*
 * Created by Noah Pritchard on 9/2/2021
 */
package noahpritchard;

import java.util.*;

public class EnglishNumberParser implements SpelledOutNumberParser {

    private final Locale locale = Locale.ENGLISH;
    private final Set<String> ignoredWords = Set.of("and");

    private final Map<String, Integer> validNumberSpellings = Map.ofEntries(
            Map.entry("one", 1),
            Map.entry("two", 2),
            Map.entry("three", 3),
            Map.entry("four", 4),
            Map.entry("five", 5),
            Map.entry("six", 6),
            Map.entry("seven", 7),
            Map.entry("eight", 8),
            Map.entry("nine", 9),
            Map.entry("ten", 10),
            Map.entry("eleven", 11),
            Map.entry("twelve", 12),
            Map.entry("thirteen", 13),
            Map.entry("fourteen", 14),
            Map.entry("fifteen", 15),
            Map.entry("sixteen", 16),
            Map.entry("seventeen", 17),
            Map.entry("eighteen", 18),
            Map.entry("nineteen", 19),
            Map.entry("twenty", 20),
            Map.entry("thirty", 30),
            Map.entry("forty", 40),
            Map.entry("fifty", 50),
            Map.entry("sixty", 60),
            Map.entry("seventy", 70),
            Map.entry("eighty", 80),
            Map.entry("ninety", 90),
            Map.entry("hundred", 100),
            Map.entry("thousand", 1000),
            Map.entry("million", 1000000),
            Map.entry("billion", 1000000000)
    );

    public EnglishNumberParser() {}

    @Override
    public Integer parseInt(String numberString) {
        List<String> words = cleanString(numberString);

        int value = 0;
        int temporaryValue = 0;

        for (String word: words) {
            Integer number = validNumberSpellings.get(word);
            if (number != null) {
                if (number < 10) {
                    //Prevent pairing a units digit with a number that already has one
                    if (temporaryValue%10 != 0) {
                        return null;
                    }
                    //Prevent pairing a units digit with ten (we have names for those numbers)
                    else if (temporaryValue == 10) {
                        return null;
                    }
                }
                else if (number < 100) { // number >= 10
                    //Prevent pairing a tens digit with a number that already has one
                    if (temporaryValue%100 != 0) {
                        return null;
                    }
                }
                //Prevent chaining scalars or scalars with no value in front
                else if (temporaryValue == 0) {
                    return null;
                }
                else if (number == 100) {
                    //Prevent pairing hundred scalar with 100+, or with a multiple of 10, or with a number that already has one
                    if (temporaryValue >= 100 || temporaryValue%10 == 0 || value%1000 != 0) {
                        return null;
                    }
                    //Prevent pairing informal thousands with a number that already has one
                    else if (temporaryValue >= 10 && value%10000 != 0) {
                        return null;
                    }
                }
                else {
                    //Prevent non-100 scalars from going out of order or repeating (e.g. thousands then millions)
                    if (value >= 1000 && number * 1000 > value) {
                        return null;
                    }
                }

                if (number >= 100) {
                    temporaryValue *= number;
                    if (temporaryValue >= 1000) {
                        value += temporaryValue;
                        temporaryValue = 0;
                    }
                }
                else {
                    temporaryValue += number;
                }
            }
            else {
                return null;
            }
        }
        value += temporaryValue;
        return value;
    }

    private List<String> cleanString(String number) {
        number = number.toLowerCase(locale);
        List<String> numberWords = new ArrayList<>(Arrays.asList(number.split(" ")));
        numberWords.removeIf(ignoredWords::contains);
        return numberWords;
    }
}
