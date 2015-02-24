package com.connorlinfoot.betterchat;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class ChannelHandler {

    private static HashMap<String, String> playerChannels = new HashMap<String, String>();

    public static void setPlayerChannel(Player player, String channel){
        setPlayerChannel(player,channel,false);
    }

    public static void setPlayerChannel(Player player, String channel, boolean force){
        if( !playerChannels.containsKey(player.getUniqueId().toString()) || force)
            playerChannels.put(player.getUniqueId().toString(), channel);
    }

    public static String getPlayerChannel(Player player){
        if( playerChannels.containsKey(player.getUniqueId().toString()))
            return playerChannels.get(player.getUniqueId().toString());
        return null;
    }

    public static boolean isInChannel(Player player, String channel){
        return playerChannels.containsKey(player.getUniqueId().toString()) && playerChannels.get(player.getUniqueId().toString()).equals(channel);
    }

}
