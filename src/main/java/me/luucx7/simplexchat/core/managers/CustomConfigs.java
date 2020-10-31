package me.luucx7.simplexchat.core.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import me.luucx7.simplexchat.SimplexChat;

public class CustomConfigs {

	public static FileConfiguration createCustomConfig(String name) {
		FileConfiguration config = null;
		File f = new File(SimplexChat.instance.getDataFolder(), name+".yml");

		if (!f.exists()) {
			f.getParentFile().mkdirs();
			SimplexChat.instance.saveResource(name+".yml", false);
		}

		config = new YamlConfiguration();
		try {
			config.load(f);
			return config;
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void saveCustomConfig(String name, FileConfiguration config) {

		new Thread(new BukkitRunnable() {

			@Override
			public void run() {
				File f = new File(SimplexChat.instance.getDataFolder(), name+".yml");
				try {
					config.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
