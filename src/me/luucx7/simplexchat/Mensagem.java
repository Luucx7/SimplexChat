package me.luucx7.simplexchat;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

public class Mensagem {
	
	final Player sender;
	String[] mensagem;
	String mensagemFinal;
	String canal;
	
	private static FileConfiguration config = SimplexChat.cConfig;

	public Mensagem(Player sender, String[] mensagem, String canal) {
		this.sender = sender;
		this.mensagem = mensagem;
		this.canal = canal;
	}
	
	public void preparar() {
		String formato = ChatColor.translateAlternateColorCodes('&', config.getString(canal+".formato"));
		String playerMsg = "";
		for (int i = 0;i<mensagem.length;i++) {
			playerMsg = playerMsg + " "+ mensagem[i];
		}

		playerMsg = PlaceholderAPI.setPlaceholders(sender, playerMsg);
		if (sender.hasPermission("chat.colorido")) {
			playerMsg = ChatColor.translateAlternateColorCodes('&', playerMsg);
		} else {
			playerMsg = ChatColor.stripColor(playerMsg);
		}
		
		formato = PlaceholderAPI.setPlaceholders(sender, formato);
		formato = formato.replace("<mensagem>", playerMsg).replace("%player%", sender.getName());
		
		this.mensagemFinal = formato;
	}
	
	public void enviar() {
		if (config.getBoolean(canal+".broadcast")) {
			if (config.getBoolean(canal+".restrito")) {
				Bukkit.broadcast(mensagemFinal, config.getString(canal+".permissao"));
				return;
			}
			Bukkit.broadcastMessage(mensagemFinal);
			return;
		}
		
		ArrayList<Player> recebedores = new ArrayList<Player>();
		int chanelRadius = config.getInt(canal+".raio");
		
		Bukkit.getOnlinePlayers().stream().filter(p -> p.getLocation().getWorld().getName().equals(sender.getLocation().getWorld().getName())).filter(p -> p.getLocation().distance(sender.getLocation())<=chanelRadius).forEach(p -> recebedores.add(p));
		
		if (config.getBoolean(canal+".restrito")) {
			final String permissão = config.getString(canal+".permissao");
			recebedores.stream().filter(r -> r.hasPermission(permissão)).forEach(r -> r.sendMessage(mensagemFinal));
			return;
		}
		
		recebedores.stream().forEach(r -> r.sendMessage(mensagemFinal));
		return;
	}
}
