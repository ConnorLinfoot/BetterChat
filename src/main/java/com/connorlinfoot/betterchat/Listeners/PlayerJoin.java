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
        ChannelHandler.setPlayerChannel(player, BetterChat.betterChat.getConfig().getString("Settings.Default Channel"));
    }

}
