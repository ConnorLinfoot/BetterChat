package com.connorlinfoot.betterchat.Listeners;

import com.connorlinfoot.betterchat.BetterChat;
import com.connorlinfoot.betterchat.ChannelHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String channel = ChannelHandler.getPlayerChannel(player);
        if (BetterChat.betterChat.getConfig().isSet("Channels." + channel + ".Permission Required")
                && !player.hasPermission("betterchat.channel." + channel)
                && !player.hasPermission("betterchat.all")) {
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to talk in this channel");
            event.setCancelled(false);
            return;
        }

        if (BetterChat.betterChat.getConfig().getBoolean("Settings.Enable Chat Color")) {
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        }

        for (Player player1 : event.getRecipients()) {
            if (!ChannelHandler.isInChannel(player1, channel)) {
                event.getRecipients().remove(player1);
            }
        }
    }

}
