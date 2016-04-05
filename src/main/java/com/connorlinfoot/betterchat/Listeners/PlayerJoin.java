package com.connorlinfoot.betterchat.Listeners;

import com.connorlinfoot.betterchat.BetterChat;
import com.connorlinfoot.betterchat.ChannelHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String channel = BetterChat.betterChat.getConfig().getString("Settings.Default Channel");
		if (BetterChat.betterChat.getConfig().getBoolean("Settings.Remember Player Channels") && BetterChat.betterChat.getConfig().isSet("Players." + player.getUniqueId().toString() + ".Channel"))
			channel = BetterChat.betterChat.getConfig().getString("Players." + player.getUniqueId().toString() + ".Channel");
		ChannelHandler.setPlayerChannel(player, channel);
	}

}
