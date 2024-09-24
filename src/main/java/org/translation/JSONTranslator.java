package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private HashMap<String, Integer> map;
    private final JSONArray jsonArray;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            jsonArray = new JSONArray(jsonString);

            map = new HashMap<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                map.put(jsonObject.getString("alpha3"), i);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        if (!map.containsKey(country)) {
            return new ArrayList<>();
        }
        int index = map.get(country);
        JSONObject jsonObject = jsonArray.getJSONObject(index);
        List<String> languages = new ArrayList<>();
        for (String key : jsonObject.keySet()) {
            if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                languages.add(key);
            }
        }
        return languages;
    }

    @Override
    public List<String> getCountries() {
        List<String> countries = new ArrayList<>();
        countries.addAll(map.keySet());
        return countries;
    }

    @Override
    public String translate(String country, String language) {
        if (!map.containsKey(country)) {
            return "Country not found";
        }
        int index = map.get(country);
        JSONObject jsonObject = jsonArray.getJSONObject(index);
        return jsonObject.getString(language);
    }
}
