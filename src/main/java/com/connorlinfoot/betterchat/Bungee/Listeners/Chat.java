package com.connorlinfoot.betterchat.Bungee.Listeners;

import com.connorlinfoot.betterchat.Bungee.BetterChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Chat implements Listener {
	private BetterChat betterChat;

	public Chat(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	@EventHandler
	public void onPlayerChat(ChatEvent event) {
		ProxiedPlayer player = (ProxiedPlayer) event.getSender();

		String channel = betterChat.getChannelHandler().getPlayerChannel(player.getUniqueId());
		if (betterChat.getConfig().getString("Channels." + channel + ".Prefix", null) != null) {
			String prefix = ChatColor.translateAlternateColorCodes('&', betterChat.getConfig().getString("Channels." + channel + ".Prefix"));
			event.setMessage(prefix + " " + event.getMessage());
		}

		if (betterChat.getConfig().getString("Channels." + channel + ".Permission Required", null) != null
				&& !player.hasPermission("betterchat.channel." + channel)
				&& !player.hasPermission("betterchat.all")) {
			player.sendMessage(ChatColor.RED + "You do not have the required permissions to talk in this channel");
			event.setCancelled(false);
			return;
		}

		if (betterChat.getConfig().getBoolean("Spam Filter.Enable Same Message Blocking") && !player.hasPermission("betterchat.staff")) {
			/* Spam Filter - Check last message sent */
			if (betterChat.getChannelHandler().lastMessages.containsKey(player.getUniqueId())) {
				if (event.getMessage().equalsIgnoreCase(betterChat.getChannelHandler().lastMessages.get(player.getUniqueId()))) {
					player.sendMessage(ChatColor.RED + "You already sent that message");
					event.setCancelled(true);
					return;
				}
			}

            /* Spam Filter - Add to last messages sent */
			betterChat.getChannelHandler().lastMessages.put(player.getUniqueId(), event.getMessage());
		}

        /* Swear Filter */
		if (betterChat.getConfig().getBoolean("Swear Filter.Enable Swear Filter") && !player.hasPermission("betterchat.staff")) {
			boolean captured = false;
			if (betterChat.getConfig().getBoolean("Swear Filter.Enable Strict Swear Filter")) {
				for (String string : betterChat.getConfig().getStringList("Swear Filter.Words To Sensor")) {
					if (event.getMessage().toLowerCase().contains(string.toLowerCase())) {
						captured = true;
						break;
					}
				}
			} else {
				for (String string : betterChat.getConfig().getStringList("Swear Filter.Words To Sensor")) {
					String message1 = " " + event.getMessage() + " ";
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
			event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
		}


		// TODO Player mentions for Bungee
		ProxiedPlayer to = (ProxiedPlayer) event.getReceiver();
		if (!betterChat.getChannelHandler().isInChannel(to.getUniqueId(), channel))
			event.setCancelled(true);
	}

}
