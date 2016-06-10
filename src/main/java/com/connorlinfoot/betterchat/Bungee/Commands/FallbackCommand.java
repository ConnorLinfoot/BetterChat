package com.connorlinfoot.betterchat.Bungee.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class FallbackCommand extends Command {

	public FallbackCommand() {
		super("betterchat", "", "channel", "whatchannel", "bc", "wc");
	}

	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + "BetterChat is currently not running");
		sender.sendMessage(ChatColor.RED + "Please check the console for any errors and reboot once fixed");
	}
}
