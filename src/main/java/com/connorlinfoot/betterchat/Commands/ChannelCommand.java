package com.connorlinfoot.betterchat.Commands;

import com.connorlinfoot.betterchat.ChannelHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChannelCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command currently only works for players");
            return false;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "You are currently talking in the \"" + ChannelHandler.getPlayerChannel(player) + "\" channel");
            return true;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String value : args) {
            stringBuilder.append(value).append(" ");
        }

        String channel = stringBuilder.toString();
        channel = channel.replaceAll("\\s+$", "");
        if (!ChannelHandler.channelExists(channel)) {
            player.sendMessage(ChatColor.RED + "The channel \"" + channel + "\" could not be found");
            return false;
        }

        if (ChannelHandler.channelUsesPerms(channel) && !player.hasPermission("betterchat.channel." + channel)) {
            player.sendMessage(ChatColor.RED + "You don't have permission to join this channel");
            return false;
        }

        ChannelHandler.setPlayerChannel(player, channel, true);
        player.sendMessage(ChatColor.GREEN + "You are now talking in the channel \"" + channel + "\"");
        return false;
    }

}
