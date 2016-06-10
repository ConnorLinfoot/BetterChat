package com.connorlinfoot.betterchat.Bukkit.Listeners;

import com.connorlinfoot.betterchat.Bukkit.BetterChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
	private BetterChat betterChat;

	public PlayerJoin(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String channel = betterChat.getConfig().getString("Settings.Default Channel");
		if (betterChat.getConfig().getBoolean("Settings.Remember Player Channels") && betterChat.getConfig().isSet("Players." + player.getUniqueId().toString() + ".Channel"))
			channel = betterChat.getConfig().getString("Players." + player.getUniqueId().toString() + ".Channel");
		betterChat.getChannelHandler().setPlayerChannel(player.getUniqueId(), channel);
	}

}
