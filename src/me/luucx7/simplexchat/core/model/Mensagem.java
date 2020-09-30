package me.luucx7.simplexchat.core.model;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.luucx7.simplexchat.core.api.Channel;
import me.luucx7.simplexchat.core.minedown.MineDown;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;

public class Mensagem {

	final Player sender;
	String[] mensagem;
	BaseComponent[] mensagemFinal;
	Channel canal;

	public Mensagem(Player sender, String[] mensagem, Channel canal) {
		this.sender = sender;
		this.mensagem = mensagem;
		this.canal = canal;
	}

	public Mensagem preparar() {
		String formato = ChatColor.translateAlternateColorCodes('&', canal.getFormat());
		String playerMsg = mensagem[0];
		if (mensagem.length>1) {
			for (int i = 1;i<mensagem.length;i++) {
				playerMsg = playerMsg + " "+ mensagem[i];
			}
		}

		if (sender.hasPermission("chat.colored")) {
			playerMsg = ChatColor.translateAlternateColorCodes('&', playerMsg);
		} else {
			playerMsg = ChatColor.stripColor(playerMsg).replace("", "")
					.replace("show_entity=", "")
					.replace("show_item=", "");
		}

		formato = formato.replace("<message>", playerMsg)
				.replace("<player>", sender.getName()
				);

		mensagemFinal = MineDown.parse(PlaceholderAPI.setPlaceholders(sender, formato).replace("<br>", "\n"));
		return this;
	}

	public void enviar() {
		if (canal.isBroadcast()) {
			if (canal.isRestrict()) {
				Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission(canal.getPermission())).forEach(p -> p.spigot().sendMessage(mensagemFinal));
				return;
			}
			Bukkit.getOnlinePlayers().stream().forEach(p -> p.spigot().sendMessage(mensagemFinal));
			return;
		}

		ArrayList<Player> recebedores = new ArrayList<Player>();
		int chanelRadius = canal.getRadius();

		Bukkit.getOnlinePlayers().stream().filter(p -> p.getLocation().getWorld().getName().equals(sender.getLocation().getWorld().getName())).filter(p -> p.getLocation().distance(sender.getLocation())<=chanelRadius).forEach(p -> recebedores.add(p));

		if (canal.isRestrict()) {
			final String permissão = canal.getPermission();
			recebedores.stream().filter(r -> r.hasPermission(permissão)).forEach(r -> r.spigot().sendMessage(mensagemFinal));
			return;
		}

		recebedores.stream().forEach(r -> r.spigot().sendMessage(mensagemFinal));
		return;
	}
}
