package me.luucx7.simplexchat;

import org.bukkit.plugin.java.JavaPlugin;

import me.luucx7.simplexchat.cmds.Global;
import me.luucx7.simplexchat.core.placeholders.PlayerColor;
import me.luucx7.simplexchat.listeners.Local;

public class SimplexChat extends JavaPlugin {
	
	public static SimplexChat instance;
	
	public void onEnable() {
		saveDefaultConfig();
		
		instance = this;
		
		new PlayerColor(this).register();
		
		this.getCommand("g").setExecutor(new Global());
		this.getCommand("chatcor").setExecutor(new Cores());
		
		this.getServer().getPluginManager().registerEvents(new Local(), this);
	}

}
