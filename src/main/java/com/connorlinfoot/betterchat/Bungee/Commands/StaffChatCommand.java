package com.connorlinfoot.betterchat.Bungee.Commands;

import com.connorlinfoot.betterchat.Bungee.BetterChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {
	private BetterChat betterChat;

	public StaffChatCommand(BetterChat betterChat) {
		super("staffchat", "", "sc");
		this.betterChat = betterChat;
	}

	public void execute(CommandSender sender, String[] args) {
		if (!betterChat.getConfig().getBoolean("Settings.Staff Chat Enabled")) {
			sender.sendMessage(ChatColor.RED + "Staff chat is disabled");
			return;
		}

		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(ChatColor.RED + "This command currently only works for players");
			return;
		}

		ProxiedPlayer player = (ProxiedPlayer) sender;
		if (!player.hasPermission("betterchat.staff")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to run this command");
			return;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Correct usage: /staffchat <message>");
			return;
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (String value : args) {
			stringBuilder.append(value).append(" ");
		}

		String message = stringBuilder.toString();
		if (betterChat.getConfig().getBoolean("Settings.Enable Chat Color")) {
			message = ChatColor.translateAlternateColorCodes('&', message);
		}

		String prefix = ChatColor.translateAlternateColorCodes('&', betterChat.getConfig().getString("Settings.Staff Chat Prefix"));
		boolean playerMentions = betterChat.getConfig().getBoolean("Settings.Enable Player Mentions");
		for (ProxiedPlayer player1 : betterChat.getProxy().getPlayers()) {
			if (player1.hasPermission("betterchat.staff")) {
				player1.sendMessage(prefix + " " + ChatColor.GOLD + player.getDisplayName() + ChatColor.RESET + ": " + message);
//				if (playerMentions) {
				// TODO (If possible)
//					if (message.toLowerCase().contains(" " + player1.getName().toLowerCase())) {
//						player1.playSound(player1.getLocation(), Sound.CHEST_OPEN, 1, 1);
//					}
//				}
			}
		}
	}

}
