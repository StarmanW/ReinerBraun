package com.starmanw.rbb.utility;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Utility {
	public static Properties loadConfiguration() {
		try {
			InputStream input = Utility.class.getClassLoader().getResourceAsStream("config.properties");
			Properties prop = new Properties();

			// Input null check
			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				return null;
			}

			// Load properties file
			prop.load(input);
			return prop;
		} catch (Exception ex) {
			System.out.println(String.format("Error loading properties: %s", ex.getMessage()));
			ex.printStackTrace();
		}
		return null;
	}

	public static JSONObject loadLocale(String locale) {
		try {
			JSONParser jsonParser = new JSONParser();
			String localeFile = Utility.class.getClassLoader().getResource(String.format("i18n/%s.json", locale))
					.getFile();
			FileReader reader = new FileReader(localeFile);

			// Read JSON file
			Object obj = jsonParser.parse(reader);
			return (JSONObject) obj;
		} catch (Exception ex) {
			System.out.println(String.format("Error loading locale: %s", ex.getMessage()));
			ex.printStackTrace();
		}
		return null;
	}
}
