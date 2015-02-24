package com.connorlinfoot.betterchat.Commands;

import com.connorlinfoot.betterchat.BetterChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.hasPermission("betterchat.staff")) {
                player1.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Staff Chat" + ChatColor.GRAY + "] " + ChatColor.GOLD + player.getDisplayName() + " " + ChatColor.RESET + message);
            }
        }

        return false;
    }

}
