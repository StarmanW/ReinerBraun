package com.starmanw.rbb.model;

import java.util.Properties;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.json.simple.JSONObject;
import com.starmanw.rbb.api.DiscordApiEventHandler;
import com.starmanw.rbb.command.FunCommand;
import com.starmanw.rbb.command.UtilityCommand;
import com.starmanw.rbb.utility.Utility;

/**
 * ReinerBraun class represents the bot
 */
public class ReinerBraun {
	// Logger
	private static final Logger LOGGER = LogManager.getLogger(ReinerBraun.class);

	// Properties
	public String prefix;
	private String locale;
	private JSONObject localeObj;
	private Properties prop;
	private DiscordApi api;

	/**
	 * Constructor method
	 */
	public ReinerBraun() {
		// Initialize bot
		prop = Utility.loadConfiguration();
		prefix = prop.getProperty("prefix");
		initializeAPI();
		initializeCommands();
		initializeLocale();
	}

	/**
	 * Initialize bot connection to discord API.
	 */
	private void initializeAPI() {
		LOGGER.log(Level.INFO, "Initializing Discord API");
		api = new DiscordApiBuilder().setToken(prop.getProperty("token")).setWaitForServersOnStartup(false).login()
				.thenApply(DiscordApiEventHandler::successAuthenticate)
				.exceptionally(DiscordApiEventHandler::errorAuthenticate).join();
	}

	/**
	 * Initialize bot commands
	 */
	private void initializeCommands() {
		LOGGER.log(Level.INFO, "Initializing bot commands");
		api.addMessageCreateListener(new UtilityCommand(this));
		api.addMessageCreateListener(new FunCommand(this));
	}

	/**
	 * Initialize bot locale settings
	 */
	private void initializeLocale() {
		LOGGER.log(Level.INFO, "Initializing bot locale");
		locale = prop.getProperty("locale");
		localeObj = Utility.loadLocale(locale);
	}

	/**
	 * Disconnect bot connection with Discord API and quit bot.
	 */
	public void quit() {
		api.disconnect();
		LOGGER.log(Level.INFO, "Bot successfully terminated...");
		System.exit(0);
	}

	public String getInviteLinks() {
		return api.createBotInvite();
	}

	public String getLocaleMessage(String localeMsgKey) {
		return (localeObj.get(localeMsgKey) == null) ? "" : localeObj.get(localeMsgKey).toString();
	}
}
