package com.connorlinfoot.betterchat.Commands;

import com.connorlinfoot.betterchat.BetterChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BetterChatCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		if (args.length == 1 && (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r"))) {
			if (sender instanceof Player && !sender.hasPermission("betterchat.admin")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to run this");
				return false;
			}

			if (!BetterChat.betterChat.getConfig().isSet("Channels." + BetterChat.betterChat.getConfig().getString("Settings.Default Channel") + ".Permission Required")) {
				sender.sendMessage(ChatColor.RED + "Error: You must have a default channel setup");
				return false;
			}

			BetterChat.betterChat.reloadConfig();
			sender.sendMessage(ChatColor.GREEN + "The BetterChat configuration has been reloaded");
			return true;
		}

		sender.sendMessage(ChatColor.AQUA + BetterChat.betterChat.getDescription().getName() + " - Version " + BetterChat.betterChat.getDescription().getVersion() + " - By Connor Linfoot");
		return true;
	}

}
