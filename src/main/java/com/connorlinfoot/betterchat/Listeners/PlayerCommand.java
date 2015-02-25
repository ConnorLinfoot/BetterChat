package com.connorlinfoot.betterchat.Listeners;

import com.connorlinfoot.betterchat.BetterChat;
import com.connorlinfoot.betterchat.ChannelHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommand implements Listener {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) return;
        for (String channel : BetterChat.betterChat.getConfig().getStringList("Channels")) {
            if (BetterChat.betterChat.getConfig().isSet("Channels." + channel + ".Command")) {
                String command = BetterChat.betterChat.getConfig().getString("Channels." + channel + ".Command");
                if (event.getMessage().equalsIgnoreCase(command)) {
                    String[] args = event.getMessage().split("\\s+");
                    Player player = event.getPlayer();
                    event.setCancelled(true);

                    if (ChannelHandler.channelUsesPerms(channel) && !player.hasPermission("betterchat.channel." + channel) && !player.hasPermission("betterchat.all")) {
                        player.sendMessage(ChatColor.RED + "You don't have permission to join this channel");
                        return;
                    }

                    if (args.length == 0) {
                        player.sendMessage(ChatColor.RED + "Correct Usage: /" + command + " <message>");
                        return;
                    }

                    String message = event.getMessage().replaceFirst(args[0] + " ", event.getMessage());
                    if (BetterChat.betterChat.getConfig().getBoolean("Settings.Enable Chat Color")) {
                        message = ChatColor.translateAlternateColorCodes('&', message);
                    }

                    String prefix = "&7[&b" + channel + "&7]";
                    if (BetterChat.betterChat.getConfig().isSet("Channels." + channel + ".Prefix")) {
                        prefix = BetterChat.betterChat.getConfig().getString("Channels." + channel + ".Prefix");
                    }
                    prefix = ChatColor.translateAlternateColorCodes('&', prefix);

                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        if (player1.hasPermission("betterchat.channel." + channel)) {
                            player1.sendMessage(prefix + " " + ChatColor.GOLD + player.getDisplayName() + " " + ChatColor.RESET + message);
                        }
                    }
                }
            }
        }
    }

}
