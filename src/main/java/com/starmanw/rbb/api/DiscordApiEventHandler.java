package com.starmanw.rbb.api;

import java.util.Iterator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import com.starmanw.rbb.model.ReinerBraun;

public class DiscordApiEventHandler {
	private static final Logger LOGGER = LogManager.getLogger(ReinerBraun.class);

	/**
	 * Handles errors while authenticating the bot
	 *
	 * @param throwable
	 * @return
	 */
	public static DiscordApi errorAuthenticate(Throwable throwable) {
		LOGGER.log(Level.ERROR, "Error authenticating bot");
		System.exit(-1);
		return null;
	}

	public static DiscordApi successAuthenticate(DiscordApi api) {
		Iterator<Server> iterator = api.getServers().iterator();
		while (iterator.hasNext()) {
			Server server = iterator.next();
			LOGGER.log(Level.INFO,
					String.format("Bot is now online on: %s [Server ID: %s]", server.getName(), server.getId()));
		}
		LOGGER.log(Level.INFO, "Discord API successfully initialized.");
		return api;
	}
}
