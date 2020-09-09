package com.starmanw.rbb.command;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import com.starmanw.rbb.model.ReinerBraun;

public class Command implements MessageCreateListener {
	// Properties
	protected ReinerBraun client;
	protected HashMap<String, String> commandsMap;

	/**
	 * Empty param constructor
	 */
	protected Command() {
	}

	/**
	 * Public constructor Takes in ReinerBraun as client
	 *
	 * @param client
	 */
	protected Command(ReinerBraun client) {
		this.client = client;
		commandsMap = new HashMap<>();
	}

	/**
	 * Listener method - Listens for new messages from channels.
	 *
	 * @param event
	 */
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		// Ignore non-bot commands
		if (!event.getMessageContent().startsWith(client.prefix)) {
			return;
		}

		// Split contents into command and arguments
		String content[] = event.getMessageContent().substring(client.prefix.length()).split(" "),
				command = content[0].toLowerCase(), args[] = content;

		// Invoke the respective command method
		try {
			for (Map.Entry en : commandsMap.entrySet()) {
				if (en.getKey().equals(command)) {
					Method method = this.getClass().getDeclaredMethod(
							String.format("command%s", (String) en.getValue()), MessageCreateEvent.class);
					method.invoke(this, event);
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
