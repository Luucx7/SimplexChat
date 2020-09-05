package me.luucx7.simplexchat.core;

import java.util.LinkedHashMap;

import org.bukkit.configuration.file.FileConfiguration;

import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.cmds.Canais;
import me.luucx7.simplexchat.core.model.Canal;

public class CanaisManager {
	
	public static LinkedHashMap<Canal, CommandManager> canaisCache = new LinkedHashMap<Canal, CommandManager>();
	
	private static FileConfiguration config = SimplexChat.cConfig;

	public static void load() {
		config.getKeys(false).stream().filter(key -> !key.equals("local")).forEach(key -> {
			Canal canal = new Canal(key);
			
			CommandManager comando = new CommandManager(canal.getComando());
			comando.register(new Canais(canal.getComando()));
			
			canaisCache.put(canal, comando);
		});
	}
	
	public static void disable() {
		canaisCache.keySet().stream().forEach(canal -> {
			canaisCache.get(canal).unregister(canal.getComando());
		});
	}
}
