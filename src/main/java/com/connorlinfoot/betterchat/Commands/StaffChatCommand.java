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
        if (BetterChat.betterChat.getConfig().getBoolean("Settings.Enable Chat Color")) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        String prefix = ChatColor.translateAlternateColorCodes('&', BetterChat.betterChat.getConfig().getString("Settings.Staff Chat Prefix"));
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.hasPermission("betterchat.staff")) {
                player1.sendMessage(prefix + " " + ChatColor.GOLD + player.getDisplayName() + " " + ChatColor.RESET + message);
            }
        }

        return false;
    }

}
