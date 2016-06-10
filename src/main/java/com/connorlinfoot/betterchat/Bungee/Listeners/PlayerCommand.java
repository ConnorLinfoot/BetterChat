package com.connorlinfoot.betterchat.Bungee.Listeners;

import com.connorlinfoot.betterchat.Bungee.BetterChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

public class PlayerCommand implements Listener {
	private BetterChat betterChat;

	public PlayerCommand(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	@EventHandler
	public void onPlayerCommand(ChatEvent event) {
		if (event.isCancelled()) return;
		Configuration channels = betterChat.getConfig().getSection("Channels");
		for (String channel : channels.getKeys()) {
			if (betterChat.getConfig().getString("Channels." + channel + ".Command", null) != null) {
				String command = betterChat.getConfig().getString("Channels." + channel + ".Command");
				String[] args = event.getMessage().split("\\s+");
				if (args[0].equalsIgnoreCase("/" + command)) {
					ProxiedPlayer player = (ProxiedPlayer) event.getSender();
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
					if (betterChat.getConfig().getString("Channels." + channel + ".Prefix", null) != null) {
						prefix = betterChat.getConfig().getString("Channels." + channel + ".Prefix");
					}
					prefix = ChatColor.translateAlternateColorCodes('&', prefix);

					boolean playerMentions = betterChat.getConfig().getBoolean("Settings.Enable Player Mentions");
					for (ProxiedPlayer player1 : betterChat.getProxy().getPlayers()) {
						if (player1.hasPermission("betterchat.channel." + channel)) {
							player1.sendMessage(prefix + " " + ChatColor.GOLD + player.getDisplayName() + ChatColor.RESET + ": " + message);
//							if (playerMentions) {
							// TODO (if possible)
//								if (message.contains(" " + player1.getName().toLowerCase())) {
//									player1.playSound(player1.getLocation(), Sound.CHEST_OPEN, 1, 1);
//								}
//							}
						}
					}
				}
			}
		}
	}

}
