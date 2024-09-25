package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {
    public static final String QUIT = "quit";
    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */

    public static void main(String[] args) {

        Translator translator1 = new JSONTranslator();
        Translator translator = new InLabByHandTranslator();
        runProgram(translator1);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        CountryCodeConverter ctranslator = new CountryCodeConverter();
        LanguageCodeConverter ltranslator = new LanguageCodeConverter();

        while (true) {
            String country = promptForCountry(translator);
            if (QUIT.equals(country)) {
                break;
            }
            String countryCode = ctranslator.fromCountry(country);

            String language = promptForLanguage(translator, countryCode);
            if (QUIT.equals(language)) {
                break;
            }
            String languageCode = ltranslator.fromLanguage(language);

            System.out.println(country + " in " + language + " is " + translator.translate(countryCode, languageCode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        CountryCodeConverter ctranslator = new CountryCodeConverter();

        List<String> countryCode = translator.getCountries();
        List<String> countries = new ArrayList<>();
        for (String cunt : countryCode) {
            countries.add(ctranslator.fromCountryCode(cunt));
        }
        Collections.sort(countries);

        for (String country: countries) {
            System.out.println(country);
        }

        System.out.println("select a country from above:");
        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    private static String promptForLanguage(Translator translator, String country) {
        LanguageCodeConverter ltranslator = new LanguageCodeConverter();

        List<String> languageCode = translator.getCountryLanguages(country);
        List<String> languages = new ArrayList<String>();
        for (String lang : languageCode) {
            languages.add(ltranslator.fromLanguageCode(lang));
        }
        Collections.sort(languages);

        for (String lang : languages) {
            System.out.println(lang);
        }

        System.out.println("select a language from above:");
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
