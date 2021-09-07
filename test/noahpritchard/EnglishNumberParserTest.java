/*
 * Created by Noah Pritchard on 9/2/2021
 */
package noahpritchard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnglishNumberParserTest {

    EnglishNumberParser parser = new EnglishNumberParser();

    //Testing different kinds and scales of numbers
    @Test
    public void one_returns_1() {
        Integer result = parser.parseInt("one");
        Assertions.assertEquals(1, result);
    }

    @Test
    public void twenty_one_returns_21() {
        Integer result = parser.parseInt("twenty one");
        Assertions.assertEquals(21, result);
    }

    @Test
    public void one_hundred_returns_100() {
        Integer result = parser.parseInt("one hundred");
        Assertions.assertEquals(100, result);
    }

    @Test
    public void one_hundred_one_returns_101() {
        Integer result = parser.parseInt("one hundred one");
        Assertions.assertEquals(101, result);
    }

    @Test
    public void one_hundred_and_one_returns_101() {
        Integer result = parser.parseInt("one hundred and one");
        Assertions.assertEquals(101, result);
    }

    @Test
    public void one_hundred_twenty_one_returns_121() {
        Integer result = parser.parseInt("one hundred twenty one");
        Assertions.assertEquals(121, result);
    }

    @Test
    public void one_thousand_one_returns_1001() {
        Integer result = parser.parseInt("one thousand one");
        Assertions.assertEquals(1001, result);
    }

    @Test
    public void one_thousand_five_hundred_twenty_one_returns_1521() {
        Integer result = parser.parseInt("one thousand five hundred twenty one");
        Assertions.assertEquals(1521, result);
    }

    @Test
    public void one_billion_forty_two_million_three_hundred_thousand_twelve_returns_1042300012() {
        Integer result = parser.parseInt("one billion forty two million three hundred thousand twelve");
        Assertions.assertEquals(1042300012, result);
    }

    //Testing invalid words
    @Test
    public void invalid_word_returns_null() {
        Integer result = parser.parseInt("friday");
        Assertions.assertNull(result);
    }

    @Test
    public void invalid_word_in_valid_words_returns_null() {
        Integer result = parser.parseInt("twenty one friday");
        Assertions.assertNull(result);
    }

    //Testing various incorrect tens and units issues
    @Test
    public void two_unit_numbers_returns_null() {
        Integer result = parser.parseInt("one two");
        Assertions.assertNull(result);
    }

    @Test
    public void tens_after_units_returns_null() {
        Integer result = parser.parseInt("one twenty");
        Assertions.assertNull(result);
    }

    @Test
    public void two_tens_numbers_returns_null() {
        Integer result = parser.parseInt("ten twenty");
        Assertions.assertNull(result);
    }

    @Test
    public void incorrect_teen_number_returns_null() {
        Integer result = parser.parseInt("ten three");
        Assertions.assertNull(result);
    }

    @Test
    public void teen_with_unit_returns_null() {
        Integer result = parser.parseInt("eleven one");
        Assertions.assertNull(result);
    }


    //Testing scalars (hundreds, thousands, etc.)
    @Test
    public void scalar_without_value_returns_null() {
        Integer result = parser.parseInt("hundred");
        Assertions.assertNull(result);
    }

    @Test
    public void stacking_scalars_returns_null() {
        Integer result = parser.parseInt("one hundred hundred");
        Assertions.assertNull(result);
    }

    @Test
    public void repeated_scalar_returns_null() {
        Integer result = parser.parseInt("one thousand three thousand");
        Assertions.assertNull(result);
    }

    @Test
    public void repeated_hundred_without_appropriate_scalar_returns_null() {
        Integer result = parser.parseInt("one hundred one hundred");
        Assertions.assertNull(result);
    }

    @Test
    public void incorrect_scalar_ordering_returns_null() {
        Integer result = parser.parseInt("one thousand one million");
        Assertions.assertNull(result);
    }

    //Testing informal thousands (eleven hundred = 1100)
    @Test
    public void informal_thousands_by_hundreds_returns_correct_value() {
        Integer result = parser.parseInt("eleven hundred");
        Assertions.assertEquals(1100, result);
    }

    @Test
    public void informal_thousands_by_hundreds_with_existing_thousands_returns_null() {
        Integer result = parser.parseInt("nine hundred twenty one thousand eleven hundred");
        Assertions.assertNull(result);
    }

    @Test
    public void informal_thousands_by_hundreds_later_returns_correct_value() {
        Integer result = parser.parseInt("nine hundred twenty thousand eleven hundred");
        Assertions.assertEquals(921100, result);
    }

    @Test
    public void informal_thousands_by_hundreds_equals_exact_thousand_returns_null() {
        Integer result = parser.parseInt("twenty hundred");
        Assertions.assertNull(result);
    }

    @Test
    public void informal_thousands_by_hundreds_with_extra_hundreds_returns_null() {
        Integer result = parser.parseInt("eleven hundred three hundred");
        Assertions.assertNull(result);
    }

    @Test
    public void informal_thousands_by_hundreds_with_extra_thousands_returns_null() {
        Integer result = parser.parseInt("eleven hundred three thousand");
        Assertions.assertNull(result);
    }

    @Test
    public void informal_thousands_by_hundreds_with_extra_thousands_2_returns_null() {
        Integer result = parser.parseInt("ninety nine hundred ninety nine thousand");
        Assertions.assertNull(result);
    }

    @Test
    public void informal_thousands_by_hundreds_with_extra_thousands_3_returns_null() {
        Integer result = parser.parseInt("ninety nine hundred ninety nine thousand");
        Assertions.assertNull(result);
    }












}