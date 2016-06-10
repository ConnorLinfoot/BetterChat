package com.connorlinfoot.betterchat.Bungee.Listeners;

import com.connorlinfoot.betterchat.Bungee.BetterChat;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoin implements Listener {
	private BetterChat betterChat;

	public PlayerJoin(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	@EventHandler
	public void onPlayerJoin(PostLoginEvent event) {
		ProxiedPlayer player = event.getPlayer();
		String channel = betterChat.getConfig().getString("Settings.Default Channel");
		if (betterChat.getConfig().getBoolean("Settings.Remember Player Channels") && betterChat.getConfig().getString("Players." + player.getUniqueId().toString() + ".Channel", null) != null)
			channel = betterChat.getConfig().getString("Players." + player.getUniqueId().toString() + ".Channel");
		betterChat.getChannelHandler().setPlayerChannel(player.getUniqueId(), channel);
	}

}
