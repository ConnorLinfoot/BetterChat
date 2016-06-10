package com.connorlinfoot.betterchat.Bungee.Commands;

import com.connorlinfoot.betterchat.Bukkit.BetterChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BetterChatCommand extends Command {
	private BetterChat betterChat;

	public BetterChatCommand(BetterChat betterChat) {
		super("betterchat", "", "bc");
		this.betterChat = betterChat;
	}

	public void execute(CommandSender sender, String[] args) {
		if (args.length == 1 && (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r"))) {
			if (sender instanceof ProxiedPlayer && !sender.hasPermission("betterchat.admin")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to run this");
				return;
			}

			if (!betterChat.getConfig().isSet("Channels." + betterChat.getConfig().getString("Settings.Default Channel") + ".Permission Required")) {
				sender.sendMessage(ChatColor.RED + "Error: You must have a default channel setup");
				return;
			}

			betterChat.reloadConfig();
			sender.sendMessage(ChatColor.GREEN + "The BetterChat configuration has been reloaded");
			return;
		}

		sender.sendMessage(ChatColor.AQUA + betterChat.getDescription().getName() + " - Version " + betterChat.getDescription().getVersion() + " - By Connor Linfoot");
	}

}
