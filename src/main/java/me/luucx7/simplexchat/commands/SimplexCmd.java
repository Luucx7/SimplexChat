package me.luucx7.simplexchat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SimplexCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String arg, String[] args) {
		if (args.length==0) {
			s.sendMessage("§b§lSimplexChat §r§b"+Bukkit.getServer().getPluginManager().getPlugin("SimplexChat").getDescription().getVersion()+" by Luucx7");
			return true;
		}
		return false;
	}
}
