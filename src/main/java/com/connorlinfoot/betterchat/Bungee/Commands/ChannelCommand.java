package com.connorlinfoot.betterchat.Bungee.Commands;

import com.connorlinfoot.betterchat.Bungee.BetterChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChannelCommand extends Command {
	private BetterChat betterChat;

	public ChannelCommand(BetterChat betterChat) {
		super("channel");
		this.betterChat = betterChat;
	}

	public void execute(net.md_5.bungee.api.CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(ChatColor.RED + "This command currently only works for players");
			return;
		}

		ProxiedPlayer player = (ProxiedPlayer) sender;
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GREEN + "You are currently talking in the \"" + betterChat.getChannelHandler().getPlayerChannel(player.getUniqueId()) + "\" channel");
			return;
		}

		boolean isAdmin = player.hasPermission("betterchat.admin");
		switch (args[0].toLowerCase()) {
			default:
				StringBuilder stringBuilder = new StringBuilder();
				for (String value : args) {
					stringBuilder.append(value).append(" ");
				}

				String channel = stringBuilder.toString();
				channel = channel.replaceAll("\\s+$", "");
				if (!betterChat.getChannelHandler().channelExists(channel)) {
					player.sendMessage(ChatColor.RED + "The channel \"" + channel + "\" could not be found");
					return;
				}

				if (betterChat.getChannelHandler().channelUsesPerms(channel) && !player.hasPermission("betterchat.channel." + channel) && !player.hasPermission("betterchat.all")) {
					player.sendMessage(ChatColor.RED + "You don't have permission to join this channel");
					return;
				}

				betterChat.getChannelHandler().setPlayerChannel(player.getUniqueId(), channel, true);
				player.sendMessage(ChatColor.GREEN + "You are now talking in the channel \"" + channel + "\"");
				break;
			case "help":
				player.sendMessage(ChatColor.AQUA + "/channel - View what channel you are currently in");
				player.sendMessage(ChatColor.AQUA + "/channel <name> - Switch channels");
				if (isAdmin) player.sendMessage(ChatColor.AQUA + "/channel create <name> - Create a channel");
				if (isAdmin) player.sendMessage(ChatColor.AQUA + "/channel delete <name> - Delete a channel");
//            	if( isAdmin ) player.sendMessage(ChatColor.AQUA + "/channel edit <name> <option> <value> - Edit a channel option");
				if (isAdmin)
					player.sendMessage(ChatColor.AQUA + "/channel info <name> - View information on a channel");
				break;
			case "create":
				if (!isAdmin) {
					player.sendMessage(ChatColor.RED + "You do not have permission to run this");
					return;
				}

				if (args.length < 2) {
					player.sendMessage(ChatColor.RED + "Correct Usage: /channel create <name>");
					return;
				}

				stringBuilder = new StringBuilder();
				for (String value : args) {
					stringBuilder.append(value).append(" ");
				}

				channel = stringBuilder.toString();
				channel = channel.replaceFirst(args[0] + " ", "");
				channel = channel.replaceAll("\\s+$", "");

				if (betterChat.getChannelHandler().channelExists(channel)) {
					player.sendMessage(ChatColor.RED + "That channel already exists");
					return;
				}

				betterChat.getChannelHandler().createChannel(channel);
				player.sendMessage(ChatColor.GREEN + "The channel \"" + channel + "\" has been created");
				break;
			case "delete":
				if (!isAdmin) {
					player.sendMessage(ChatColor.RED + "You do not have permission to run this");
					return;
				}

				if (args.length < 2) {
					player.sendMessage(ChatColor.RED + "Correct Usage: /channel delete <name>");
					return;
				}

				stringBuilder = new StringBuilder();
				for (String value : args) {
					stringBuilder.append(value).append(" ");
				}

				channel = stringBuilder.toString();
				channel = channel.replaceFirst(args[0] + " ", "");
				channel = channel.replaceAll("\\s+$", "");

				if (!betterChat.getChannelHandler().channelExists(channel)) {
					player.sendMessage(ChatColor.RED + "That channel doesn't exist");
					return;
				}

				betterChat.getChannelHandler().deleteChannel(channel);
				player.sendMessage(ChatColor.GREEN + "The channel \"" + channel + "\" has been deleted");
				break;
			case "info":
				if (!isAdmin) {
					player.sendMessage(ChatColor.RED + "You do not have permission to run this");
					return;
				}

				if (args.length < 2) {
					player.sendMessage(ChatColor.RED + "Correct Usage: /channel info <name>");
					return;
				}

				stringBuilder = new StringBuilder();
				for (String value : args) {
					stringBuilder.append(value).append(" ");
				}

				channel = stringBuilder.toString();
				channel = channel.replaceFirst(args[0] + " " + args[1] + " ", "");
				channel = channel.replaceAll("\\s+$", "");

				if (!betterChat.getChannelHandler().channelExists(channel)) {
					player.sendMessage(ChatColor.RED + "That channel doesn't exist");
					return;
				}

				betterChat.getChannelHandler().channelInfo(channel, player.getUniqueId());
				break;
		}
	}

}
