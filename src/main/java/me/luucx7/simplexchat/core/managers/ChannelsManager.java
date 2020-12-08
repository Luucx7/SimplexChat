package me.luucx7.simplexchat.core.managers;

import java.util.LinkedHashMap;
import java.util.Optional;

import me.luucx7.simplexchat.core.api.Local;
import org.bukkit.configuration.file.FileConfiguration;

import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.commands.ChannelCommand;
import me.luucx7.simplexchat.core.api.Channel;
import me.luucx7.simplexchat.core.model.channels.ChannelImpl;
import me.luucx7.simplexchat.core.model.channels.LocalImpl;

public class ChannelsManager {
	
	public static LinkedHashMap<Channel, CommandManager> canaisCache = new LinkedHashMap<Channel, CommandManager>();
	public static Local local;
	
	private static FileConfiguration config = SimplexChat.cConfig;

	public static void load() {
		
		// Loads the local channel
		local = new LocalImpl();
		CommandManager localCmd = new CommandManager(local.getCommand());
		localCmd.unregister(local.getCommand());
		localCmd.register(new ChannelCommand(local.getCommand()));

		canaisCache.put(local, localCmd);
		
		// Loads all other channels
		config.getKeys(false).stream().filter(key -> !key.equalsIgnoreCase("local")).forEach(key -> {
			Channel canal = new ChannelImpl(key);
			
			CommandManager comando = new CommandManager(canal.getCommand());
			comando.register(new ChannelCommand(canal.getCommand()));
			
			canaisCache.put(canal, comando);
		});
	}
	
	public static void disable() {
		canaisCache.keySet().stream().forEach(canal -> {
			canaisCache.get(canal).unregister(canal.getCommand());
		});
	}

	public static Channel getChannel(String channelName) {
		Optional<Channel> canalOP = canaisCache.keySet().stream().filter(key -> key.getName().equalsIgnoreCase(channelName)).findAny();
		if (canalOP.isPresent()) return canalOP.get();
		return null;
	}
}
