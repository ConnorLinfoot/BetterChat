package com.connorlinfoot.betterchat.Bukkit.Listeners;

import com.connorlinfoot.betterchat.Bukkit.BetterChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommand implements Listener {
	private BetterChat betterChat;

	public PlayerCommand(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if (event.isCancelled()) return;
		ConfigurationSection channels = betterChat.getConfig().getConfigurationSection("Channels");
		for (String channel : channels.getKeys(false)) {
			if (betterChat.getConfig().isSet("Channels." + channel + ".Command")) {
				String command = betterChat.getConfig().getString("Channels." + channel + ".Command");
				String[] args = event.getMessage().split("\\s+");
				if (args[0].equalsIgnoreCase("/" + command)) {
					Player player = event.getPlayer();
					event.setCancelled(true);

					if (betterChat.getChannelHandler().channelUsesPerms(channel) && !player.hasPermission("betterchat.channel." + channel) && !player.hasPermission("betterchat.all")) {
						player.sendMessage(ChatColor.RED + "You don't have permission to talk in this channel");
						return;
					}

					if (args.length == 1) {
						player.sendMessage(ChatColor.RED + "Correct Usage: /" + command + " <message>");
						return;
					}

					String message = event.getMessage().replaceFirst(args[0] + " ", "");

					if (betterChat.getConfig().getBoolean("Spam Filter.Enable Same Message Blocking") && !player.hasPermission("betterchat.staff")) {
						/* Spam Filter - Check last message sent */
						if (betterChat.getChannelHandler().lastMessages.containsKey(player.getUniqueId())) {
							if (message.equalsIgnoreCase(betterChat.getChannelHandler().lastMessages.get(player.getUniqueId()))) {
								player.sendMessage(ChatColor.RED + "You already sent that message");
								event.setCancelled(true);
								return;
							}
						}

                        /* Spam Filter - Add to last messages sent */
						betterChat.getChannelHandler().lastMessages.put(player.getUniqueId(), message);
					}

                    /* Swear Filter */
					if (betterChat.getConfig().getBoolean("Swear Filter.Enable Swear Filter") && !player.hasPermission("betterchat.staff")) {
						boolean captured = false;
						if (betterChat.getConfig().getBoolean("Swear Filter.Enable Strict Swear Filter")) {
							for (String string : betterChat.getConfig().getStringList("Swear Filter.Words To Sensor")) {
								if (message.toLowerCase().contains(string.toLowerCase())) {
									captured = true;
									break;
								}
							}
						} else {
							for (String string : betterChat.getConfig().getStringList("Swear Filter.Words To Sensor")) {
								String message1 = " " + message + " ";
								if (message1.toLowerCase().contains(" " + string.toLowerCase() + " ")) {
									captured = true;
									break;
								}
							}
						}

						if (captured) {
							player.sendMessage(ChatColor.RED + "Please do not use bad language on the server");
							event.setCancelled(true);
							return;
						}
					}

					if (betterChat.getConfig().getBoolean("Settings.Enable Chat Color")) {
						message = ChatColor.translateAlternateColorCodes('&', message);
					}

					String prefix = "&7[&b" + channel + "&7]";
					if (betterChat.getConfig().isSet("Channels." + channel + ".Prefix")) {
						prefix = betterChat.getConfig().getString("Channels." + channel + ".Prefix");
					}
					prefix = ChatColor.translateAlternateColorCodes('&', prefix);

					boolean playerMentions = betterChat.getConfig().getBoolean("Settings.Enable Player Mentions");
					for (Player player1 : Bukkit.getOnlinePlayers()) {
						if (player1.hasPermission("betterchat.channel." + channel)) {
							player1.sendMessage(prefix + " " + ChatColor.GOLD + player.getDisplayName() + ChatColor.RESET + ": " + message);
							if (playerMentions) {
								if (message.contains(" " + player1.getName().toLowerCase())) {
									player1.playSound(player1.getLocation(), Sound.CHEST_OPEN, 1, 1);
								}
							}
						}
					}
				}
			}
		}
	}

}
