package com.connorlinfoot.betterchat;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class ChannelHandler {

    private static HashMap<String, String> playerChannels = new HashMap<String, String>();

    public static void setPlayerChannel(Player player, String channel) {
        setPlayerChannel(player, channel, false);
    }

    public static void setPlayerChannel(Player player, String channel, boolean force) {
        if (!playerChannels.containsKey(player.getUniqueId().toString()) || force) {
            playerChannels.put(player.getUniqueId().toString(), channel);
            if (BetterChat.betterChat.getConfig().getBoolean("Settings.Remember Player Channels")) {
                BetterChat.betterChat.getConfig().set("Players." + player.getUniqueId().toString() + ".Channel", channel);
                BetterChat.betterChat.saveConfig();
            }
        }
    }

    public static String getPlayerChannel(Player player) {
        if (!playerChannels.containsKey(player.getUniqueId().toString()))
            playerChannels.put(player.getUniqueId().toString(), BetterChat.betterChat.getConfig().getString("Settings.Default Channel"));
        return playerChannels.get(player.getUniqueId().toString());
    }

    public static boolean isInChannel(Player player, String channel) {
        return playerChannels.containsKey(player.getUniqueId().toString()) && playerChannels.get(player.getUniqueId().toString()).equals(channel);
    }

    public static boolean channelExists(String channel) {
        return BetterChat.betterChat.getConfig().isSet("Channels." + channel + ".Permission Required");
    }

    public static boolean channelUsesPerms(String channel) {
        return BetterChat.betterChat.getConfig().getBoolean("Channels." + channel + ".Permission Required");
    }

    public static void clearChannel(Player player) {
        if (playerChannels.containsKey(player.getUniqueId().toString()))
            playerChannels.remove(player.getUniqueId().toString());
    }

    public static void createChannel(String channel) {
        if (channelExists(channel)) return;
        BetterChat.betterChat.getConfig().set("Channels." + channel + ".Permission Required", false);
        BetterChat.betterChat.saveConfig();
    }

    public static void deleteChannel(String channel) {
        if (!channelExists(channel)) return;
        BetterChat.betterChat.getConfig().set("Channels." + channel, null);
        BetterChat.betterChat.saveConfig();
    }

}
