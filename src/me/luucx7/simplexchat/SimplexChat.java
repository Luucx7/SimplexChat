package me.luucx7.simplexchat;

import org.bukkit.plugin.java.JavaPlugin;

import me.luucx7.simplexchat.cmds.SimplexCmd;
import me.luucx7.simplexchat.core.CanaisManager;
import me.luucx7.simplexchat.core.placeholders.PlayerColor;
import me.luucx7.simplexchat.listeners.Local;

public class SimplexChat extends JavaPlugin {
	
	public static SimplexChat instance;
	
	public void onEnable() {
		saveDefaultConfig();
		
		instance = this;
		
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
