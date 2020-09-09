package com.starmanw.rbb.command;

import org.javacord.api.event.message.MessageCreateEvent;

import com.starmanw.rbb.api.CommandInterface;
import com.starmanw.rbb.model.ReinerBraun;

public class FunCommand extends Command implements CommandInterface {

	/**
	 * Public constructor Takes in ReinerBraun as client
	 *
	 * @param client
	 */
	public FunCommand(ReinerBraun client) {
		super(client);
		registerCommands();
	}

	@Override
	public void registerCommands() {
		// Register commands
		commandsMap.put("rps", "Rps");
		commandsMap.put("tictactoe", "TicTacToe");
		commandsMap.put("ttt", "TicTacToe");
	}

	protected void commandRps(MessageCreateEvent event) {
		try {
			event.getChannel().sendMessage("RPS: TO BE IMPLEMENTED");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void commandTicTacToe(MessageCreateEvent event) {
		try {
			event.getChannel().sendMessage("\\~\\~~ Tic Tac Toe ~~~");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
