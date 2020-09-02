package me.luucx7.simplexchat.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.luucx7.simplexchat.Mensagem;
import me.luucx7.simplexchat.SimplexChat;

public class Global implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String arg, String[] args) {
		if (s instanceof Player) {
			if (arg.length()==0) {
				s.sendMessage("§cUso: /g <mensagem>");
				return true;
			}
			
			Bukkit.getScheduler().runTaskAsynchronously(SimplexChat.instance, () -> {
				Mensagem msg = new Mensagem((Player) s, args, "global");
				msg.preparar();
				msg.enviar();
			});
			
		}
		return false;
	}

}
