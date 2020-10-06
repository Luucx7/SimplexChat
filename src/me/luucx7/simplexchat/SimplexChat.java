package me.luucx7.simplexchat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.luucx7.simplexchat.cmds.Cores;
import me.luucx7.simplexchat.cmds.SimplexCmd;
import me.luucx7.simplexchat.core.managers.CanaisManager;
import me.luucx7.simplexchat.core.managers.CustomConfigs;
import me.luucx7.simplexchat.core.managers.FocusManager;
import me.luucx7.simplexchat.core.placeholders.ChatPlaceholder;
import me.luucx7.simplexchat.listeners.ChatPlayerListener;
import me.luucx7.simplexchat.listeners.LocalListener;

public class SimplexChat extends JavaPlugin {
	
	// literally this
	public static SimplexChat instance;
	
	// Channels
	public static FileConfiguration cConfig;
	
	// Colors
	public static FileConfiguration colorsConfig;
	
	// Focus
	public static FileConfiguration fConfig;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
			
		cConfig = CustomConfigs.createCustomConfig("channels");
		
		new ChatPlaceholder(this).register();
		
		this.getCommand("simplexchat").setExecutor(new SimplexCmd());
		CanaisManager.load();
		
		if (getConfig().getBoolean("modules.focus")) {
			fConfig = CustomConfigs.createCustomConfig("focus");
			FocusManager.prepare();
		}
		if (getConfig().getBoolean("modules.chatcolor")) {
			colorsConfig = CustomConfigs.createCustomConfig("color");
			
			this.getCommand("chatcor").setExecutor(new Cores());
		}
		
		this.getServer().getPluginManager().registerEvents(new LocalListener(), this);
		this.getServer().getPluginManager().registerEvents(new ChatPlayerListener(), this);
	}
	
	public void onDisable() {
		CanaisManager.disable();
		FocusManager.unload();
	}
}
