package com.starmanw.rbb.command;

import java.util.concurrent.ExecutionException;
import org.javacord.api.event.message.MessageCreateEvent;

import com.starmanw.rbb.api.CommandInterface;
import com.starmanw.rbb.model.ReinerBraun;

public class UtilityCommand extends Command implements CommandInterface {
	/**
	 * Public constructor Takes in ReinerBraun as client
	 *
	 * @param client
	 */
	public UtilityCommand(ReinerBraun client) {
		super(client);
		registerCommands();
	}

	/**
	 * Register all utility commands
	 */
	@Override
	public void registerCommands() {
		// Register commands
		commandsMap.put("clr", "DeleteMessages");
		commandsMap.put("quit", "Quit");
		commandsMap.put("geninvitelink", "GenerateInviteLink");
	}

	protected void commandDeleteMessages(MessageCreateEvent event) {
		try {
			event.getMessage().delete().join();
			event.getChannel().bulkDelete(event.getChannel().getMessagesBefore(50, event.getMessage()).get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	protected void commandQuit(MessageCreateEvent event) {
		if (event.getMessageAuthor().isServerAdmin()) {
			event.getChannel().sendMessage(client.getLocaleMessage("msg_quit"));
		}
		client.quit();
	}

	protected void commandGenerateInviteLink(MessageCreateEvent event) {
		try {
			if (event.getMessageAuthor().isServerAdmin()) {
				String inviteLinks = String.format(client.getLocaleMessage("msg_generate_invite_link"),
						client.getInviteLinks());
				event.getChannel().sendMessage(inviteLinks);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
