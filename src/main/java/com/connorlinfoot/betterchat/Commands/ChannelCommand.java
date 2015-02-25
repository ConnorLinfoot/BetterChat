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

        boolean isAdmin = player.hasPermission("betterchat.admin");
        if (args[0].equalsIgnoreCase("help")) {
            player.sendMessage(ChatColor.AQUA + "/channel - View what channel you are currently in");
            player.sendMessage(ChatColor.AQUA + "/channel <name> - Switch channels");
            if (isAdmin) player.sendMessage(ChatColor.AQUA + "/channel create <name> - Create a channel");
            if (isAdmin) player.sendMessage(ChatColor.AQUA + "/channel delete <name> - Delete a channel");
//            if( isAdmin ) player.sendMessage(ChatColor.AQUA + "/channel edit <name> <option> <value> - Edit a channel option");
//            if( isAdmin ) player.sendMessage(ChatColor.AQUA + "/channel info <name> - View information on a channel");
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (!isAdmin) {
                player.sendMessage(ChatColor.RED + "You do not have permission to run this");
                return false;
            }

            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Correct Usage: /channel create <name>");
                return false;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (String value : args) {
                stringBuilder.append(value).append(" ");
            }

            String channel = stringBuilder.toString();
            channel = channel.replaceFirst(args[0] + " " + args[1] + " ", "");
            channel = channel.replaceAll("\\s+$", "");

            if (ChannelHandler.channelExists(channel)) {
                player.sendMessage(ChatColor.RED + "That channel already exists");
                return false;
            }

            ChannelHandler.createChannel(channel);
            player.sendMessage(ChatColor.GREEN + "The channel \"" + channel + "\" has been created");
            return true;
        }

        if (args[0].equalsIgnoreCase("delete")) {
            if (!isAdmin) {
                player.sendMessage(ChatColor.RED + "You do not have permission to run this");
                return false;
            }

            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Correct Usage: /channel delete <name>");
                return false;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (String value : args) {
                stringBuilder.append(value).append(" ");
            }

            String channel = stringBuilder.toString();
            channel = channel.replaceFirst(args[0] + " " + args[1] + " ", "");
            channel = channel.replaceAll("\\s+$", "");

            if (!ChannelHandler.channelExists(channel)) {
                player.sendMessage(ChatColor.RED + "That channel doesn't exist");
                return false;
            }

            ChannelHandler.deleteChannel(channel);
            player.sendMessage(ChatColor.GREEN + "The channel \"" + channel + "\" has been deleted");
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

        if (ChannelHandler.channelUsesPerms(channel) && !player.hasPermission("betterchat.channel." + channel) && !player.hasPermission("betterchat.all")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to join this channel");
            return false;
        }

        ChannelHandler.setPlayerChannel(player, channel, true);
        player.sendMessage(ChatColor.GREEN + "You are now talking in the channel \"" + channel + "\"");
        return false;
    }

}
