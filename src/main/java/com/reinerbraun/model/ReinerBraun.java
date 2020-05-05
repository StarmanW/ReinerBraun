package com.reinerbraun.model;

import com.reinerbraun.command.UtilityCommand;
import com.reinerbraun.util.Utility;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.Properties;

/**
 * ReinerBraun class represents the bot
 */
public class ReinerBraun {
    // Properties
    private Properties prop;
    private DiscordApi api;
    public String prefix;

    /**
     * Constructor method
     */
    public ReinerBraun() {
        // Initialize bot
        this.prop = Utility.loadConfiguration();
        this.prefix = this.prop.getProperty("prefix");
        initializeAPI();
        initializeEvents();
    }

    /**
     * Initialize bot connection
     * to discord API.
     */
    private void initializeAPI() {
        this.api = new DiscordApiBuilder()
                .setToken(this.prop.getProperty("token"))
                .setWaitForServersOnStartup(false)
                .login()
                .thenApply(ReinerBraun::successAuthenticate)
                .exceptionally(ReinerBraun::errorAuthenticate)
                .join();
    }

    /**
     * Initialize bot events listener
     */
    private void initializeEvents() {
        this.api.addMessageCreateListener(new UtilityCommand(this));
    }

    /**
     * Disconnect bot connection with
     * Discord API and quit bot.
     */
    public void quit() {
        this.api.disconnect();
    }

    /**
     * Handles errors while authenticating the bot
     *
     * @param throwable
     * @return
     */
    private static DiscordApi errorAuthenticate(Throwable throwable) {
        System.out.println("Error authenticating bot");
        System.exit(-1);
        return null;
    }

    private static DiscordApi successAuthenticate(DiscordApi api) {
        for (Object guild: api.getServers().toArray()) {
            System.out.println(String.format("Bot is now online on: %s", guild.toString()));
        }
        return api;
    }
}
