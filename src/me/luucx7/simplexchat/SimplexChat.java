package me.luucx7.simplexchat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.luucx7.simplexchat.cmds.Cores;
import me.luucx7.simplexchat.cmds.SimplexCmd;
import me.luucx7.simplexchat.core.CanaisManager;
import me.luucx7.simplexchat.core.CustomConfigs;
import me.luucx7.simplexchat.core.placeholders.PlayerColor;
import me.luucx7.simplexchat.listeners.Local;

public class SimplexChat extends JavaPlugin {
	
	public static SimplexChat instance;
	public static FileConfiguration cConfig;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
			
		cConfig = CustomConfigs.createCustomConfig("channels");
		
		new PlayerColor(this).register();
		
		this.getCommand("chatcor").setExecutor(new Cores());
		this.getCommand("simplexchat").setExecutor(new SimplexCmd());
		CanaisManager.load();
		
		this.getServer().getPluginManager().registerEvents(new Local(), this);
	}
	
	public void onDisable() {
		CanaisManager.disable();
	}
}
