package com.connorlinfoot.betterchat;

import com.connorlinfoot.betterchat.Commands.*;
import com.connorlinfoot.betterchat.Listeners.Chat;
import com.connorlinfoot.betterchat.Listeners.PlayerCommand;
import com.connorlinfoot.betterchat.Listeners.PlayerJoin;
import com.connorlinfoot.betterchat.Listeners.PlayerQuit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterChat extends JavaPlugin implements Listener {
	public static BetterChat betterChat;
	public static boolean SNAPSHOT = false;

	public void onEnable() {
		betterChat = this;
		getConfig().options().copyDefaults(true);
		saveConfig();
		Server server = getServer();
		ConsoleCommandSender console = server.getConsoleSender();

		if (!getConfig().isSet("Channels." + getConfig().getString("Settings.Default Channel") + ".Permission Required")) {
			registerCommands(true);
			console.sendMessage("");
			console.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
			console.sendMessage("");
			console.sendMessage(ChatColor.RED + getDescription().getName() + " failed to start!");
			console.sendMessage(ChatColor.RED + "Default channel is not setup!");
			console.sendMessage("");
			console.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
			console.sendMessage("");
			return;
		}

		registerEvents();
		registerCommands(false);

		console.sendMessage("");
		console.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		console.sendMessage("");
		console.sendMessage(ChatColor.AQUA + getDescription().getName());
		console.sendMessage(ChatColor.AQUA + "Version " + getDescription().getVersion());
		if (getDescription().getVersion().contains("SNAPSHOT")) {
			SNAPSHOT = true;
			console.sendMessage(ChatColor.RED + "You are running a snapshot build of " + getDescription().getName() + " please report bugs!");
			console.sendMessage(ChatColor.RED + "NO support will be given if running old snapshot build!");
		}
		console.sendMessage("");
		console.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		console.sendMessage("");
	}

	public void onDisable() {
		getLogger().info(getDescription().getName() + " has been disabled!");
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new Chat(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerCommand(), this);
	}

	private void registerCommands(boolean fallback) {
		if (fallback) {
			FallbackCommand fallbackCommand = new FallbackCommand();
			Bukkit.getPluginCommand("channel").setExecutor(fallbackCommand);
			Bukkit.getPluginCommand("staffchat").setExecutor(fallbackCommand);
			Bukkit.getPluginCommand("whatchannel").setExecutor(fallbackCommand);
			Bukkit.getPluginCommand("betterchat").setExecutor(fallbackCommand);
			Bukkit.getPluginCommand("mute").setExecutor(fallbackCommand);
			return;
		}
		Bukkit.getPluginCommand("channel").setExecutor(new ChannelCommand());
		Bukkit.getPluginCommand("staffchat").setExecutor(new StaffChatCommand());
		Bukkit.getPluginCommand("whatchannel").setExecutor(new WhatChannelCommand());
		Bukkit.getPluginCommand("betterchat").setExecutor(new BetterChatCommand());
		Bukkit.getPluginCommand("mute").setExecutor(new MuteCommand());
	}
}
