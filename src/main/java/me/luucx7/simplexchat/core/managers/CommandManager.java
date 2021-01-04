package me.luucx7.simplexchat.core.managers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import me.luucx7.simplexchat.SimplexChat;

public class CommandManager {
   
    private String cmdName;
    private CommandMap cmdMap;
    
    public CommandManager(String cmdName) {
       
        this.cmdName = cmdName;
       
        try {
       
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
       
        this.cmdMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
       
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Bukkit.getConsoleSender().sendMessage("§cError while registering command "+cmdName);
        }
       
    }
   
    public void register(Command cmd) {
        this.cmdMap.register(this.cmdName, cmd);
    }
    
    public void unregister(final String command) {
		if (SimplexChat.instance.getServer() != null && SimplexChat.instance.getServer().getPluginManager() instanceof SimplePluginManager) {
			final SimplePluginManager manager = (SimplePluginManager) SimplexChat.instance.getServer().getPluginManager();
			try {
				final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
				field.setAccessible(true);
				cmdMap = (CommandMap) field.get(manager);
				final Field field2 = SimpleCommandMap.class.getDeclaredField("knownCommands");
				field2.setAccessible(true);
				@SuppressWarnings("unchecked")
				final Map<String, org.bukkit.command.Command> knownCommands = (Map<String, org.bukkit.command.Command>) field2.get(cmdMap);
				for (final Map.Entry<String, org.bukkit.command.Command> entry : knownCommands.entrySet()) {
					if (entry.getKey().equals(command)) {
						entry.getValue().unregister(cmdMap);
					}
				}
				knownCommands.remove(command);
			} catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException | SecurityException e) {
				//
			}
		}
	}

    
    public CommandMap getCommandMap() {
    	this.cmdMap.getCommand("g").unregister(cmdMap);
    	return this.cmdMap;
    }
    
    public void setDescription(String description) {
       
        this.cmdMap.getCommand(this.cmdName).setDescription(description);
       
    }
   
    public String getDescription() {
       
        return this.cmdMap.getCommand(this.cmdName).getDescription();
       
    }
   
    public void setPermission(String permission) {
       
        this.cmdMap.getCommand(this.cmdName).setPermission(permission);
       
    }
   
    public String getPermission() {
       
        return this.cmdMap.getCommand(this.cmdName).getPermission();
       
    }
   
    public void setPermissionMsg(String permissionMsg) {
       
        this.cmdMap.getCommand(this.cmdName).setPermissionMessage(permissionMsg);
       
    }
   
    public String getPermissionMsg() {
       
        return this.cmdMap.getCommand(this.cmdName).getPermissionMessage();
       
    }
   
    public void setUsage(String usage) {
       
        this.cmdMap.getCommand(this.cmdName).setUsage(usage);
       
    }
   
    public String getUsage() {
       
        return this.cmdMap.getCommand(this.cmdName).getUsage();
       
    }
   
    public void setAliases(List<String> aliases) {

        this.cmdMap.getCommand(this.cmdName).setAliases(aliases);
       
    }
   
    public List<String> getAliases() {

        return this.cmdMap.getCommand(this.cmdName).getAliases();
       
    }
}

