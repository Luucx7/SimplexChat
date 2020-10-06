package me.luucx7.simplexchat.core.model;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.api.Channel;
import me.luucx7.simplexchat.core.minedown.MineDown;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class Mensagem {

	final Player sender;
	String[] mensagem;
	String consoleMsg;
	BaseComponent[] mensagemFinal;
	Channel canal;
	int quantia;

	public Mensagem(Player sender, String[] mensagem, Channel canal) {
		this.sender = sender;
		this.mensagem = mensagem;
		this.canal = canal;
		this.quantia = 0;
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
					.replace("show_item=", "")
					.replace("&", "")
					.replace("color", "")
					;
		}

		formato = formato.replace("<message>", playerMsg)
				.replace("<player>", sender.getName()
				);

		mensagemFinal = MineDown.parse(PlaceholderAPI.setPlaceholders(sender, formato).replace("<br>", "\n"));
		
		if (SimplexChat.instance.getConfig().getBoolean("log_to_console")) {
			consoleMsg = SimplexChat.instance.getConfig().getString("console_log");
			consoleMsg = consoleMsg.replace("<channel>", canal.getName())
			.replace("<channelCmd>", canal.getCommand())
			.replace("<player>", sender.getName())
			.replace("<message>", playerMsg)
			;
			
			consoleMsg = PlaceholderAPI.setPlaceholders(sender, consoleMsg);
		}
		return this;
	}

	public void enviar() {
		ArrayList<Player> recebedores = new ArrayList<Player>();
		
		if (canal.isBroadcast()) {
			Bukkit.getOnlinePlayers().stream().forEach(p -> {
				recebedores.add(p);
				if (!p.hasPermission("chat.bypasscount")) quantia++;
			});
		} else {
			int chanelRadius = canal.getRadius();
			Bukkit.getOnlinePlayers().stream().filter(p -> p.getLocation().getWorld().getName().equals(sender.getLocation().getWorld().getName())).filter(p -> p.getLocation().distance(sender.getLocation())<=chanelRadius).forEach(p -> {
				recebedores.add(p);
				if (!p.hasPermission("chat.bypasscount")) quantia++;
			});
		}
		
		if (canal.isRestrict()) {
			recebedores.stream().filter(r -> r.hasPermission(canal.getPermission())).forEach(r -> r.spigot().sendMessage(mensagemFinal));
		} else {
			recebedores.stream().forEach(r -> r.spigot().sendMessage(mensagemFinal));
		}
		
		if (canal.useActionbar()) {
			String actionMessage = quantia>1 ? 
					ChatColor.translateAlternateColorCodes('&', SimplexChat.instance.getConfig().getString("amount_readed").replace("<amount>", (quantia-1)+""))
					: ChatColor.translateAlternateColorCodes('&', SimplexChat.instance.getConfig().getString("no_one"));
			
		   sender.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionMessage));
		}
		if (SimplexChat.instance.getConfig().getBoolean("log_to_console")) {
			Bukkit.getConsoleSender().sendMessage(consoleMsg);
		}
		return;
	}
}
