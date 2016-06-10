package com.connorlinfoot.betterchat.Bungee;

import com.connorlinfoot.betterchat.Shared.ChannelHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeeChannelHandler extends ChannelHandler {
	private BetterChat betterChat;

	public BungeeChannelHandler(BetterChat betterChat) {
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
		return betterChat.getConfig().getString("Channels." + channel + ".Prefix", null);
	}

	public boolean channelExists(String channel) {
		return false; // TODO
	}

	public boolean channelUsesPerms(String channel) {
		return betterChat.getConfig().getBoolean("Channels." + channel + ".Permission Required");
	}

	public String translateAlternateColorCodes(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public void sendMessage(UUID uuid, String message) {
		ProxiedPlayer player = betterChat.getProxy().getPlayer(uuid);
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
