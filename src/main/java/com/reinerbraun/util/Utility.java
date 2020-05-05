package com.reinerbraun.util;

import org.javacord.api.DiscordApi;

import java.io.InputStream;
import java.util.Properties;

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

    public static void generateInviteLink(DiscordApi api) {
        // Print the invite url of your bot
        System.out.println("You can invite the bot by using this URL: " + api.createBotInvite());
    }
}
