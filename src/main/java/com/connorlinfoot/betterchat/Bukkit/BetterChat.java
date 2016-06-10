package com.connorlinfoot.betterchat.Bukkit;

import com.connorlinfoot.betterchat.Bukkit.Commands.*;
import com.connorlinfoot.betterchat.Bukkit.Listeners.Chat;
import com.connorlinfoot.betterchat.Bukkit.Listeners.PlayerCommand;
import com.connorlinfoot.betterchat.Bukkit.Listeners.PlayerJoin;
import com.connorlinfoot.betterchat.Bukkit.Listeners.PlayerQuit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterChat extends JavaPlugin implements Listener {
	private BetterChat betterChat;
	private BukkitChannelHandler channelHandler;
	public static boolean SNAPSHOT = false;

	public void onEnable() {
		betterChat = this;
		getConfig().options().copyDefaults(true);
		saveConfig();
		Server server = getServer();
		ConsoleCommandSender console = server.getConsoleSender();

		if (!getConfig().isSet("Channels." + getConfig().getString("Settings.Default Channel") + ".Permission Required")) {
			registerCommands(true);
			console.sendMessage(ChatColor.RED + getDescription().getName() + " failed to start!");
			console.sendMessage(ChatColor.RED + "Default channel is not setup!");
			return;
		}

		registerEvents();
		registerCommands(false);
		channelHandler = new BukkitChannelHandler(this);

		console.sendMessage("");
		console.sendMessage(ChatColor.AQUA + getDescription().getName());
		console.sendMessage(ChatColor.AQUA + "Version " + getDescription().getVersion());
		if (getDescription().getVersion().contains("SNAPSHOT")) {
			SNAPSHOT = true;
			console.sendMessage(ChatColor.RED + "You are running a snapshot build of " + getDescription().getName() + " please report bugs!");
			console.sendMessage(ChatColor.RED + "NO support will be given if running old snapshot build!");
		}
		console.sendMessage("");
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new Chat(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuit(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerCommand(this), this);
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
		Bukkit.getPluginCommand("channel").setExecutor(new ChannelCommand(this));
		Bukkit.getPluginCommand("staffchat").setExecutor(new StaffChatCommand(this));
		Bukkit.getPluginCommand("whatchannel").setExecutor(new WhatChannelCommand(this));
		Bukkit.getPluginCommand("betterchat").setExecutor(new BetterChatCommand(this));
		Bukkit.getPluginCommand("mute").setExecutor(new MuteCommand());
	}

	public BukkitChannelHandler getChannelHandler() {
		return channelHandler;
	}

}
