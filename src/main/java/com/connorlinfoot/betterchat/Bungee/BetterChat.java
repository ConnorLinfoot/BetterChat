package com.connorlinfoot.betterchat.Bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BetterChat extends Plugin {
	private Configuration configuration;
	private BungeeChannelHandler channelHandler;

	public void onEnable() {
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		channelHandler = new BungeeChannelHandler(this);
	}

	public Configuration getConfig() {
		return configuration;
	}

	public void saveConfig() {

	}

	public BungeeChannelHandler getChannelHandler() {
		return channelHandler;
	}

}
