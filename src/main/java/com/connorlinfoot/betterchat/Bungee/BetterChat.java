package com.connorlinfoot.betterchat.Bungee;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class BetterChat extends Plugin {
	private Configuration configuration;
	private BungeeChannelHandler channelHandler;

	public void onEnable() {
		File configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			try {
				if (!configFile.getParentFile().exists())
					configFile.getParentFile().mkdirs();
				configFile.createNewFile();
				InputStream is = getResourceAsStream("config.yml");
				OutputStream os = new FileOutputStream(configFile);
				ByteStreams.copy(is, os);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		channelHandler = new BungeeChannelHandler(this);
	}

	public Configuration getConfig() {
		return configuration;
	}

	public void saveConfig() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BungeeChannelHandler getChannelHandler() {
		return channelHandler;
	}

}
