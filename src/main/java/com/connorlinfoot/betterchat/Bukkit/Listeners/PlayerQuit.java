package com.connorlinfoot.betterchat.Bukkit.Listeners;

import com.connorlinfoot.betterchat.Bukkit.BetterChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
	private BetterChat betterChat;

	public PlayerQuit(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (!betterChat.getConfig().getBoolean("Settings.Remember Player Channels"))
			betterChat.getChannelHandler().clearChannel(player.getUniqueId());
	}

}
