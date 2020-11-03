package me.luucx7.simplexchat.core.managers;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.commands.Ch;
import me.luucx7.simplexchat.core.api.Channel;

public class FocusManager {
	
	public static LinkedHashMap<String, Channel> focusWorlds = new LinkedHashMap<String, Channel>();
	
	public static FileConfiguration fConfig = SimplexChat.fConfig;
	
	private static CommandManager manager;
	
	public static void prepare() {
		manager = new CommandManager("ch");
		
		List<String> aliases = new LinkedList<String>();
		manager.register(new Ch("ch"));
		aliases.add("channel");
		aliases.add("canal");
		aliases.add("canais");
		manager.setUsage("§cUso: /ch <canal>");
		manager.setAliases(aliases);
		manager.unregister("ch");
		manager.register(new Ch("ch"));
				
		fConfig.getStringList("worlds").stream().forEach(s -> {
			int doispontos = s.indexOf(':');
			String world = s.substring(0, doispontos);
			String canal = s.substring(doispontos+1);
			
			Optional<Channel> op = CanaisManager.canaisCache.keySet().stream().filter(k -> k.getName().equalsIgnoreCase(canal)).findAny();
			if (!op.isPresent()) {
				return;
			}
			
			focusWorlds.put(world, op.get());
		});
	}
	
	public static void unload() {
		if (SimplexChat.instance.getConfig().getBoolean("modules.focus")) {
			manager.unregister("ch");
		}
	}
}
