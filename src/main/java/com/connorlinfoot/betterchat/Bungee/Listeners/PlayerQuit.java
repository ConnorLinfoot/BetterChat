package com.connorlinfoot.betterchat.Bungee.Listeners;

import com.connorlinfoot.betterchat.Bungee.BetterChat;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerQuit implements Listener {
	private BetterChat betterChat;

	public PlayerQuit(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event) {
		ProxiedPlayer player = event.getPlayer();
		if (!betterChat.getConfig().getBoolean("Settings.Remember Player Channels"))
			betterChat.getChannelHandler().clearChannel(player.getUniqueId());
	}

}
