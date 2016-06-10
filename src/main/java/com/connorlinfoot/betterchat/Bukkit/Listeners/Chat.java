package com.connorlinfoot.betterchat.Bukkit.Listeners;

import com.connorlinfoot.betterchat.Bukkit.BetterChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.UUID;

public class Chat implements Listener {
	private BetterChat betterChat;

	public Chat(BetterChat betterChat) {
		this.betterChat = betterChat;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		String channel = betterChat.getChannelHandler().getPlayerChannel(player.getUniqueId());
		if (betterChat.getConfig().isSet("Channels." + channel + ".Prefix")) {
			String prefix = ChatColor.translateAlternateColorCodes('&', betterChat.getConfig().getString("Channels." + channel + ".Prefix"));
			event.setFormat(prefix + " " + event.getFormat());
		}

		if (betterChat.getConfig().isSet("Channels." + channel + ".Permission Required")
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

		boolean playerMentions = betterChat.getConfig().getBoolean("Settings.Enable Player Mentions");
		ArrayList<UUID> remove = new ArrayList<UUID>();
		for (Player player1 : event.getRecipients()) {
			if (!betterChat.getChannelHandler().isInChannel(player1.getUniqueId(), channel))
				remove.add(player1.getUniqueId());

			if (playerMentions) {
				if (event.getMessage().toLowerCase().contains(" " + player1.getName().toLowerCase())) {
					player1.playSound(player1.getLocation(), Sound.CHEST_OPEN, 1, 1);
				}
			}
		}

		for (UUID uuid : remove) {
			Player player1 = Bukkit.getPlayer(uuid);
			if (player1 == null)
				continue;
			event.getRecipients().remove(player);
		}
	}

}
