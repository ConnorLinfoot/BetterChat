package com.connorlinfoot.betterchat.Bukkit.Commands;

import com.connorlinfoot.betterchat.Bukkit.BetterChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {
	private BetterChat betterChat;

	public StaffChatCommand(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		sender.sendMessage(ChatColor.YELLOW + "//TODO");
		if (!betterChat.getConfig().getBoolean("Settings.Staff Chat Enabled")) {
			sender.sendMessage(ChatColor.RED + "Staff chat is disabled");
			return false;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command currently only works for players");
			return false;
		}

		Player player = (Player) sender;
		if (!player.hasPermission("betterchat.staff")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to run this command");
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Correct usage: /staffchat <message>");
			return false;
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
		for (Player player1 : Bukkit.getOnlinePlayers()) {
			if (player1.hasPermission("betterchat.staff")) {
				player1.sendMessage(prefix + " " + ChatColor.GOLD + player.getDisplayName() + ChatColor.RESET + ": " + message);
				if (playerMentions) {
					if (message.toLowerCase().contains(" " + player1.getName().toLowerCase())) {
						player1.playSound(player1.getLocation(), Sound.CHEST_OPEN, 1, 1);
					}
				}
			}
		}

		return false;
	}

}
