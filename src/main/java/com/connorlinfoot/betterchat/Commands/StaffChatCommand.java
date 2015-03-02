package com.connorlinfoot.betterchat.Commands;

import com.connorlinfoot.betterchat.BetterChat;
import com.connorlinfoot.betterchat.ChannelHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (!BetterChat.betterChat.getConfig().getBoolean("Settings.Staff Chat Enabled")) {
            sender.sendMessage(ChatColor.RED + "Staff chat is disabled");
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command currently only works for players");
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("betterchat.staff")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to run this command");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Correct usage: /staffchat <message>");
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String value : args) {
            stringBuilder.append(value).append(" ");
        }

        String message = stringBuilder.toString();

        if (BetterChat.betterChat.getConfig().getBoolean("Spam Filter.Enable Same Message Blocking")) {
            /* Spam Filter - Check last message sent */
            if (ChannelHandler.lastMessages.containsKey(player.getUniqueId().toString())) {
                if (message.equalsIgnoreCase(ChannelHandler.lastMessages.get(player.getUniqueId().toString()))) {
                    player.sendMessage(ChatColor.RED + "You already sent that message");
                    return false;
                }
            }

            /* Spam Filter - Add to last messages sent */
            ChannelHandler.lastMessages.put(player.getUniqueId().toString(), message);
        }

        /* Swear Filter */
        if (BetterChat.betterChat.getConfig().getBoolean("Swear Filter.Enable Swear Filter")) {
            boolean captured = false;
            if (BetterChat.betterChat.getConfig().getBoolean("Swear Filter.Enable Strict Swear Filter")) {
                for (String string1 : BetterChat.betterChat.getConfig().getStringList("Swear Filter.Words To Sensor")) {
                    if (message.toLowerCase().contains(string1.toLowerCase())) {
                        captured = true;
                        break;
                    }
                }
            } else {
                for (String string1 : BetterChat.betterChat.getConfig().getStringList("Swear Filter.Words To Sensor")) {
                    String message1 = " " + message + " ";
                    if (message1.toLowerCase().contains(" " + string1.toLowerCase() + " ")) {
                        captured = true;
                        break;
                    }
                }
            }

            if (captured) {
                player.sendMessage(ChatColor.RED + "Please do not use bad language on the server");
                return false;
            }
        }

        if (BetterChat.betterChat.getConfig().getBoolean("Settings.Enable Chat Color")) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        String prefix = ChatColor.translateAlternateColorCodes('&', BetterChat.betterChat.getConfig().getString("Settings.Staff Chat Prefix"));
        boolean playerMentions = BetterChat.betterChat.getConfig().getBoolean("Settings.Enable Player Mentions");
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.hasPermission("betterchat.staff")) {
                player1.sendMessage(prefix + " " + ChatColor.GOLD + player.getDisplayName() + ChatColor.RESET + ": " + message);
                if (playerMentions) {
                    if (message.toLowerCase().contains(" " + player1.getName().toLowerCase())) {
                        player1.playSound(player1.getLocation(), Sound.CHEST_OPEN, 1, 1);
                    }
                }
            }
        }

        return false;
    }

}
