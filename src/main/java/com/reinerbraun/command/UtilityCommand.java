package com.reinerbraun.command;

import com.reinerbraun.model.ReinerBraun;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UtilityCommand implements MessageCreateListener {
    // Properties
    private ReinerBraun client;
    private HashMap<String, String> commandsMap;

    /**
     * Public constructor
     * Takes in ReinerBraun as client
     * @param client
     */
    public UtilityCommand(ReinerBraun client) {
        this.client = client;
        this.registerCommands();
    }

    /**
     * Register all utility commands
     */
    private void registerCommands() {
        // Initialize HashMap and register commands
        this.commandsMap = new HashMap<String, String>();
        this.commandsMap.put("clr", "commandDeleteMessages");
        this.commandsMap.put("quit", "commandQuit");
    }

    /**
     * Listener method - Listens for new messages from channels.
     * @param event
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        // Ignore non-bot commands
        if (!event.getMessageContent().startsWith(this.client.prefix)) return;

        // Split contents into command and arguments
        String content[] = event.getMessageContent().substring(this.client.prefix.length()).split(" "),
                command = content[0].toLowerCase(),
                args[] = content;

        // Invoke the respective command method
        try {
            for (Map.Entry en : this.commandsMap.entrySet()) {
                if (en.getKey().equals(command)) {
                    Method method = this.getClass().getDeclaredMethod((String) en.getValue(), MessageCreateEvent.class);
                    method.invoke(this, event);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void commandDeleteMessages(MessageCreateEvent event) {
        try {
            event.getMessage().delete().join();
            event.getChannel().bulkDelete(event.getChannel()
                    .getMessagesBefore(50, event.getMessage()).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void commandQuit(MessageCreateEvent event) {
        this.client.quit();
    }
}
