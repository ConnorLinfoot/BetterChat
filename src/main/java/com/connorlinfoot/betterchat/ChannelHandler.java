package com.connorlinfoot.betterchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ChannelHandler {

	public static HashMap<String, String> lastMessages = new HashMap<String, String>();

	private static HashMap<String, String> playerChannels = new HashMap<String, String>();

	public static void setPlayerChannel(Player player, String channel) {
		setPlayerChannel(player, channel, false);
	}

	public static void setPlayerChannel(Player player, String channel, boolean force) {
		if (!playerChannels.containsKey(player.getUniqueId().toString()) || force) {
			playerChannels.put(player.getUniqueId().toString(), channel);
			if (BetterChat.betterChat.getConfig().getBoolean("Settings.Remember Player Channels")) {
				BetterChat.betterChat.getConfig().set("Players." + player.getUniqueId().toString() + ".Channel", channel);
				BetterChat.betterChat.saveConfig();
			}
		}
	}

	public static String getPlayerChannel(Player player) {
		if (!playerChannels.containsKey(player.getUniqueId().toString()))
			playerChannels.put(player.getUniqueId().toString(), BetterChat.betterChat.getConfig().getString("Settings.Default Channel"));
		return playerChannels.get(player.getUniqueId().toString());
	}

	public static boolean isInChannel(Player player, String channel) {
		return playerChannels.containsKey(player.getUniqueId().toString()) && playerChannels.get(player.getUniqueId().toString()).equals(channel);
	}

	public static boolean channelExists(String channel) {
		return BetterChat.betterChat.getConfig().isSet("Channels." + channel + ".Permission Required");
	}

	public static boolean channelUsesPerms(String channel) {
		return BetterChat.betterChat.getConfig().getBoolean("Channels." + channel + ".Permission Required");
	}

	public static void clearChannel(Player player) {
		if (playerChannels.containsKey(player.getUniqueId().toString()))
			playerChannels.remove(player.getUniqueId().toString());
	}

	public static void createChannel(String channel) {
		if (channelExists(channel)) return;
		BetterChat.betterChat.getConfig().set("Channels." + channel + ".Permission Required", false);
		BetterChat.betterChat.saveConfig();
	}

	public static void deleteChannel(String channel) {
		if (!channelExists(channel)) return;
		BetterChat.betterChat.getConfig().set("Channels." + channel, null);
		BetterChat.betterChat.saveConfig();
	}

	public static void channelInfo(String channel, Player player) {
		channelInfo(channel, player, false);
	}

	public static void channelInfo(String channel, Player player, boolean GUI) {
		if (GUI) return; // TODO add a GUI for viewing channel information
		if (player == null) return;
		if (!channelExists(channel)) return;

		String prefix = "None";
		if (BetterChat.betterChat.getConfig().isSet("Channels." + channel + ".Prefix")) {
			prefix = BetterChat.betterChat.getConfig().getString("Channels." + channel + ".Prefix");
		}
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);

		player.sendMessage("" + ChatColor.AQUA + ChatColor.STRIKETHROUGH + "--------------------------------------------");
		player.sendMessage(ChatColor.AQUA + "Channel Name: " + channel);
		player.sendMessage(ChatColor.AQUA + "Permissions Required: " + BetterChat.betterChat.getConfig().getBoolean("Channels." + channel + ".Permission Required"));
		player.sendMessage(ChatColor.AQUA + "Custom Prefix: " + prefix);
		player.sendMessage("" + ChatColor.AQUA + ChatColor.STRIKETHROUGH + "--------------------------------------------");
	}

	public static Player[] getAllPlayersInChannel(String channel) {
		Player[] players = new Player[0];
		Integer i = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (getPlayerChannel(player).equals(channel)) {
				players[i] = player;
				i++;
			}
		}
		return players;
	}

}
