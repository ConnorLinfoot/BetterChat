package com.connorlinfoot.betterchat.Bukkit.Commands;

import com.connorlinfoot.betterchat.Bukkit.BetterChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChannelCommand implements CommandExecutor {
	private BetterChat betterChat;

	public ChannelCommand(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command currently only works for players");
			return false;
		}

		Player player = (Player) sender;
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GREEN + "You are currently talking in the \"" + betterChat.getChannelHandler().getPlayerChannel(player.getUniqueId()) + "\" channel");
			return true;
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
					return false;
				}

				if (betterChat.getChannelHandler().channelUsesPerms(channel) && !player.hasPermission("betterchat.channel." + channel) && !player.hasPermission("betterchat.all")) {
					player.sendMessage(ChatColor.RED + "You don't have permission to join this channel");
					return false;
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
					return false;
				}

				if (args.length < 2) {
					player.sendMessage(ChatColor.RED + "Correct Usage: /channel create <name>");
					return false;
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
					return false;
				}

				betterChat.getChannelHandler().createChannel(channel);
				player.sendMessage(ChatColor.GREEN + "The channel \"" + channel + "\" has been created");
				break;
			case "delete":
				if (!isAdmin) {
					player.sendMessage(ChatColor.RED + "You do not have permission to run this");
					return false;
				}

				if (args.length < 2) {
					player.sendMessage(ChatColor.RED + "Correct Usage: /channel delete <name>");
					return false;
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
					return false;
				}

				betterChat.getChannelHandler().deleteChannel(channel);
				player.sendMessage(ChatColor.GREEN + "The channel \"" + channel + "\" has been deleted");
				break;
			case "info":
				if (!isAdmin) {
					player.sendMessage(ChatColor.RED + "You do not have permission to run this");
					return false;
				}

				if (args.length < 2) {
					player.sendMessage(ChatColor.RED + "Correct Usage: /channel info <name>");
					return false;
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
					return false;
				}

				betterChat.getChannelHandler().channelInfo(channel, player.getUniqueId());
				break;
		}
		return true;
	}

}
