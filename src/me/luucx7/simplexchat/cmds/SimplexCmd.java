package me.luucx7.simplexchat.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.CanaisManager;

public class SimplexCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String arg, String[] args) {
		if (args.length==0) {
			s.sendMessage("§b§lSimplexChat §r§b"+Bukkit.getServer().getPluginManager().getPlugin("SimplexChat").getDescription().getVersion()+" by Luucx7");
			return true;
		}
		if (args[0].equalsIgnoreCase("reload")) {
			if (!(s.hasPermission("simplexchat.reload"))) {
				s.sendMessage("§cVocê não tem permissão!");
				return true;
			}
			
			CanaisManager.disable();
			SimplexChat.instance.reloadConfig();
			CanaisManager.load();
			
			s.sendMessage("§aPlugin recarregado.");
		}
		return false;
	}

}
