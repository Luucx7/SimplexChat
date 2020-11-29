package me.luucx7.simplexchat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.luucx7.simplexchat.commands.Cores;
import me.luucx7.simplexchat.commands.SimplexCmd;
import me.luucx7.simplexchat.core.managers.CanaisManager;
import me.luucx7.simplexchat.core.managers.CustomConfigs;
import me.luucx7.simplexchat.core.managers.FocusManager;
import me.luucx7.simplexchat.core.placeholders.ChatPlaceholder;
import me.luucx7.simplexchat.listeners.ChatPlayerListener;
import me.luucx7.simplexchat.listeners.LocalListener;

public class SimplexChat extends JavaPlugin {
	
	// literally this
	@Getter @Setter(AccessLevel.PRIVATE)
	public static SimplexChat instance;
	
	// Channels
	public static FileConfiguration cConfig;
	
	// Colors
	public static FileConfiguration colorsConfig;
	
	// Focus
	public static FileConfiguration fConfig;

	// DiscordSRV support
	@Setter(AccessLevel.PRIVATE)
	@Getter(AccessLevel.PUBLIC)
	public static boolean isDiscordSRV;
	
	public void onEnable() {
		setInstance(this);
		setDiscordSRV(false);
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

		if (getConfig().getBoolean("modules.discordsrv") && Bukkit.getPluginManager().getPlugin("DiscordSRV")!=null && Bukkit.getPluginManager().getPlugin("DiscordSRV").isEnabled()) {
			setDiscordSRV(true);
		}
		
		this.getServer().getPluginManager().registerEvents(new LocalListener(), this);
		this.getServer().getPluginManager().registerEvents(new ChatPlayerListener(), this);
	}
	
	public void onDisable() {
		CanaisManager.disable();
		FocusManager.unload();
	}
}
