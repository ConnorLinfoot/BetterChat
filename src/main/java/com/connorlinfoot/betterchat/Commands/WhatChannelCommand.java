package com.connorlinfoot.betterchat.Commands;

import com.connorlinfoot.betterchat.ChannelHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhatChannelCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Correct Usage: /whatchannel <player>");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "That player could not be found");
            return false;
        }

        sender.sendMessage(ChatColor.GREEN + "The player \"" + player.getName() + "\" is in the \"" + ChannelHandler.getPlayerChannel(player) + "\" channel");
        return true;
    }

}
