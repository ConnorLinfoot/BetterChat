package com.connorlinfoot.betterchat.Bungee.Commands;

import com.connorlinfoot.betterchat.Bungee.BetterChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class WhatChannelCommand extends Command {
	private BetterChat betterChat;

	public WhatChannelCommand(BetterChat betterChat) {
		super("whatchannel", "", "wc");
		this.betterChat = betterChat;
	}

	public void execute(CommandSender sender, String[] args) {
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Correct Usage: /whatchannel <player>");
			return;
		}

		ProxiedPlayer player = betterChat.getProxy().getPlayer(args[0]);
		if (player == null) {
			sender.sendMessage(ChatColor.RED + "That player could not be found");
			return;
		}

		sender.sendMessage(ChatColor.GREEN + "The player \"" + player.getName() + "\" is in the \"" + betterChat.getChannelHandler().getPlayerChannel(player.getUniqueId()) + "\" channel");
	}

}
