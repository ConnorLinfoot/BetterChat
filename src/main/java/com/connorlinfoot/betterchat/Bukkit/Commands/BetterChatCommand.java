package com.connorlinfoot.betterchat.Bukkit.Commands;

import com.connorlinfoot.betterchat.Bukkit.BetterChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BetterChatCommand implements CommandExecutor {
	private BetterChat betterChat;

	public BetterChatCommand(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		if (args.length == 1 && (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r"))) {
			if (sender instanceof Player && !sender.hasPermission("betterchat.admin")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to run this");
				return false;
			}

			if (!betterChat.getConfig().isSet("Channels." + betterChat.getConfig().getString("Settings.Default Channel") + ".Permission Required")) {
				sender.sendMessage(ChatColor.RED + "Error: You must have a default channel setup");
				return false;
			}

			betterChat.reloadConfig();
			sender.sendMessage(ChatColor.GREEN + "The BetterChat configuration has been reloaded");
			return true;
		}

		sender.sendMessage(ChatColor.AQUA + betterChat.getDescription().getName() + " - Version " + betterChat.getDescription().getVersion() + " - By Connor Linfoot");
		return true;
	}

}
