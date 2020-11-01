package me.luucx7.simplexchat.commands;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.api.Channel;
import me.luucx7.simplexchat.core.managers.CanaisManager;
import me.luucx7.simplexchat.core.model.Mensagem;
import net.md_5.bungee.api.ChatColor;

public class ChannelCommand extends Command {

	public ChannelCommand(String commandName) {
		super(commandName);
	}

	@Override
	public boolean execute(CommandSender sender, String comando, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cOnly players can use this command!");
			return true;
		}
		
		Optional<Channel> opChannel = CanaisManager.canaisCache.keySet().stream().filter(canal -> canal.getCommand().equalsIgnoreCase(comando)).findFirst();
		if (!opChannel.isPresent()) {
			return true;
		}
		
		Channel canal = opChannel.get();
		
		if (canal.isRestrict()) {
			if (!sender.hasPermission(canal.getPermission())) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimplexChat.instance.getConfig().getString("no_permission")));
				return true;
			}
		}
		
		if (args.length==0) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimplexChat.instance.getConfig().getString("channel_usage_message").replace("{command}", comando.toLowerCase())));
			return true;
		}
		
		Bukkit.getScheduler().runTaskAsynchronously(SimplexChat.instance, () -> {
			Mensagem message = new Mensagem((Player) sender, args, canal);
			message.preparar().enviar();
		});
		return false;
	}

}
