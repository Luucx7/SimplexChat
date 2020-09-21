package me.luucx7.simplexchat.core.model;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.minedown.MineDown;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;

public class Mensagem {

	final Player sender;
	String[] mensagem;
	BaseComponent[] mensagemFinal;
	String canal;

	private static FileConfiguration config = SimplexChat.cConfig;

	public Mensagem(Player sender, String[] mensagem, String canal) {
		this.sender = sender;
		this.mensagem = mensagem;
		this.canal = canal;
	}

	public void preparar() {
		String formato = ChatColor.translateAlternateColorCodes('&', config.getString(canal+".formato"));
		String playerMsg = mensagem[0];
		if (mensagem.length>1) {
			for (int i = 1;i<mensagem.length;i++) {
				playerMsg = playerMsg + " "+ mensagem[i];
			}
		}

		if (sender.hasPermission("chat.colorido")) {
			playerMsg = ChatColor.translateAlternateColorCodes('&', playerMsg);
		} else {
			playerMsg = ChatColor.stripColor(playerMsg).replace("", "");
		}

		formato = formato.replace("<message>", playerMsg)
				.replace("<player>", sender.getName()
				.replace("show_entity=", "")
				.replace("show_item=", "")
				);

		mensagemFinal = MineDown.parse(PlaceholderAPI.setPlaceholders(sender, formato));
	}

	public void enviar() {
		if (config.getBoolean(canal+".broadcast")) {
			if (config.getBoolean(canal+".restrito")) {
				Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission(config.getString(canal+".permissao"))).forEach(p -> p.spigot().sendMessage(mensagemFinal));
				return;
			}
			Bukkit.getOnlinePlayers().stream().forEach(p -> p.spigot().sendMessage(mensagemFinal));
			return;
		}

		ArrayList<Player> recebedores = new ArrayList<Player>();
		int chanelRadius = config.getInt(canal+".raio");

		Bukkit.getOnlinePlayers().stream().filter(p -> p.getLocation().getWorld().getName().equals(sender.getLocation().getWorld().getName())).filter(p -> p.getLocation().distance(sender.getLocation())<=chanelRadius).forEach(p -> recebedores.add(p));

		if (config.getBoolean(canal+".restrito")) {
			final String permissão = config.getString(canal+".permissao");
			recebedores.stream().filter(r -> r.hasPermission(permissão)).forEach(r -> r.spigot().sendMessage(mensagemFinal));
			return;
		}

		recebedores.stream().forEach(r -> r.spigot().sendMessage(mensagemFinal));
		return;
	}
}
