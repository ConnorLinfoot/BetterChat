package com.connorlinfoot.betterchat.Shared;

import java.util.HashMap;
import java.util.UUID;

public abstract class ChannelHandler {
	public HashMap<UUID, String> lastMessages = new HashMap<>();
	private HashMap<UUID, String> playerChannels = new HashMap<>();

	public ChannelHandler() {

	}

	public void setPlayerChannel(UUID uuid, String channel) {
		setPlayerChannel(uuid, channel, false);
	}

	public void setPlayerChannel(UUID uuid, String channel, boolean force) {
		if (!playerChannels.containsKey(uuid) || force) {
			playerChannels.put(uuid, channel);
			if (rememberChatChannels()) {
				storeChannel(uuid, channel);
			}
		}
	}

	public String getPlayerChannel(UUID uuid) {
		if (!playerChannels.containsKey(uuid))
			playerChannels.put(uuid, getDefaultChannel());
		return playerChannels.get(uuid);
	}

	public boolean isInChannel(UUID uuid, String channel) {
		return playerChannels.containsKey(uuid) && playerChannels.get(uuid).equals(channel);
	}

	;

	public void clearChannel(UUID uuid) {
		if (playerChannels.containsKey(uuid))
			playerChannels.remove(uuid);
	}

	public void channelInfo(String channel, UUID uuid) {
		channelInfo(channel, uuid, false);
	}

	public void channelInfo(String channel, UUID uuid, boolean GUI) {
		if (GUI) return; // TODO add a GUI for viewing channel information
		if (!channelExists(channel)) return;

		String prefix = "None";
		if (getChannelPrefix(channel) != null) {
			prefix = getChannelPrefix(channel);
		}
		prefix = translateAlternateColorCodes(prefix);
		sendMessage(uuid, translateAlternateColorCodes("&b&m--------------------------------------------"));
		sendMessage(uuid, translateAlternateColorCodes("&bChannel Name: " + channel));
		sendMessage(uuid, translateAlternateColorCodes("&bPermissions Required: " + channelUsesPerms(channel)));
		sendMessage(uuid, translateAlternateColorCodes("&bCustom Prefix: " + prefix));
		sendMessage(uuid, translateAlternateColorCodes("&b&m--------------------------------------------"));
	}

	public UUID[] getAllPlayersInChannel(String channel) {
		UUID[] players = new UUID[0];
		Integer i = 0;
//		for (Player player : Bukkit.getOnlinePlayers()) { TODO
//			if (getPlayerChannel(player).equals(channel)) {
//				players[i] = player;
//				i++;
//			}
//		}
		return players;
	}

	public abstract void createChannel(String channel);

	public abstract void deleteChannel(String channel);

	public abstract String getChannelPrefix(String channel);

	public abstract boolean channelExists(String channel);

	public abstract boolean channelUsesPerms(String channel);

	public abstract String translateAlternateColorCodes(String message);

	public abstract void sendMessage(UUID uuid, String message);

	public abstract boolean rememberChatChannels();

	public abstract void storeChannel(UUID uuid, String channel);

	public abstract String getDefaultChannel();

}
