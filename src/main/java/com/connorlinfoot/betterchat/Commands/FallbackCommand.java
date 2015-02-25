package com.connorlinfoot.betterchat.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FallbackCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        sender.sendMessage(ChatColor.RED + "BetterChat is currently not running");
        sender.sendMessage(ChatColor.RED + "Please check the console for any errors and reboot once fixed");
        return true;
    }

}
