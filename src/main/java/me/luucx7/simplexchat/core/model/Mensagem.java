package me.luucx7.simplexchat.core.model;

import java.util.ArrayList;

import de.themoep.minedown.MineDown;
import github.scarsz.discordsrv.DiscordSRV;
import me.luucx7.simplexchat.core.nms.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.api.Channel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.springframework.util.StringUtils;

public class Mensagem {

	Player sender;
	String[] mensagem;
	String mensagemString;
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

	public Mensagem (Player sender, String message, Channel channel) {
		this(sender, message.split(" "), channel);
	}

	public Mensagem preparar() {
		String formato = ChatColor.translateAlternateColorCodes('&', canal.getFormat());
		mensagemString = mensagem[0];
		if (mensagem.length>1) {
			for (int i = 1;i<mensagem.length;i++) {
				mensagemString = mensagemString + " "+ mensagem[i];
			}
		}

		if (sender!=null) if (sender.hasPermission("chat.colored")) {
			mensagemString = ChatColor.translateAlternateColorCodes('&', mensagemString);
		} else {
			mensagemString = ChatColor.stripColor(mensagemString).replace("", "")
					.replace("show_entity=", "")
					.replace("show_item=", "")
					.replace("&", "")
					.replace("color", "")
					;
		}

		if (SimplexChat.useFilter && !sender.hasPermission("chat.filter.bypass")) {

			SimplexChat.filterConfig.getStringList("remove").stream().forEach(s -> {
				mensagemString = mensagemString.replace(s, "");
			});

			SimplexChat.filterConfig.getStringList("replace").stream().forEach(s -> {
				String toReplace = s.substring(0, s.indexOf(':'));
				String replacer = s.substring(s.indexOf(':')+1);

				mensagemString = mensagemString.replace(toReplace, replacer);
			});
		}

		formato = formato.replace("<message>", mensagemString);
		if (sender!=null) formato = formato.replace("<player>", sender.getName());

		String replacedMessage = PlaceholderAPI.setPlaceholders(sender, formato).replace("<br>", "\n");
		mensagemFinal = MineDown.parse(replacedMessage.trim().replaceAll(" +", " "));
		
		if (SimplexChat.instance.getConfig().getBoolean("log_to_console")) {
			consoleMsg = SimplexChat.instance.getConfig().getString("console_log");
			consoleMsg = consoleMsg.replace("<channel>", canal.getName())
			.replace("<channelCmd>", canal.getCommand())
			.replace("<player>", sender!=null ? sender.getName() : "Discord")
			.replace("<message>", mensagemString)
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

			if (SimplexChat.getInstance().getServer().getVersion().contains("1.8")) {
				ActionBar.sendActionBar(sender, actionMessage);
			} else {
				sender.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionMessage));
			}
		}
		if (SimplexChat.instance.getConfig().getBoolean("log_to_console")) {
			Bukkit.getConsoleSender().sendMessage(consoleMsg);
		}

		if (SimplexChat.isDiscordSRV()) {
			if (DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName(canal.getName()) == null) return;
			if (StringUtils.isEmpty(mensagem.toString())) return;
			DiscordSRV.getPlugin().processChatMessage(sender, mensagemString, canal.getName(), false);
		}
		return;
	}
}
