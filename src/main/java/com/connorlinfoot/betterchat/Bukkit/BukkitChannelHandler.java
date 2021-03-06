package com.connorlinfoot.betterchat.Bukkit;

import com.connorlinfoot.betterchat.Shared.ChannelHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitChannelHandler extends ChannelHandler {
	private BetterChat betterChat;

	public BukkitChannelHandler(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	public void createChannel(String channel) {
		if (channelExists(channel)) return;
		betterChat.getConfig().set("Channels." + channel + ".Permission Required", false);
		betterChat.saveConfig();
	}

	public void deleteChannel(String channel) {
		if (!channelExists(channel)) return;
		betterChat.getConfig().set("Channels." + channel, null);
		betterChat.saveConfig();
	}

	public String getChannelPrefix(String channel) {
		if (!betterChat.getConfig().isSet("Channels." + channel + ".Prefix"))
			return null;
		return betterChat.getConfig().getString("Channels." + channel + ".Prefix");
	}

	public boolean channelExists(String channel) {
		return betterChat.getConfig().isSet("Channels." + channel + ".Permission Required");
	}

	public boolean channelUsesPerms(String channel) {
		return betterChat.getConfig().getBoolean("Channels." + channel + ".Permission Required");
	}

	public String translateAlternateColorCodes(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public void sendMessage(UUID uuid, String message) {
		Player player = Bukkit.getPlayer(uuid);
		if (player != null)
			player.sendMessage(message);
	}

	public boolean rememberChatChannels() {
		return betterChat.getConfig().getBoolean("Settings.Remember Player Channels");
	}

	public void storeChannel(UUID uuid, String channel) {
		betterChat.getConfig().set("Players." + uuid.toString() + ".Channel", channel);
		betterChat.saveConfig();
	}

	public String getDefaultChannel() {
		return betterChat.getConfig().getString("Settings.Default Channel");
	}

}
