package me.luucx7.simplexchat.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import me.luucx7.simplexchat.SimplexChat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.luucx7.simplexchat.core.api.Channel;
import me.luucx7.simplexchat.core.managers.ChannelsManager;
import me.luucx7.simplexchat.core.managers.JogadorManager;

public class Ch extends Command {

	public Ch(String name) {
		super(name);
	}

	@Override
	public boolean execute(CommandSender s, String c, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage(getMessage("players_only"));
			return true;
		}
		if (args.length==0) {
			s.sendMessage(getMessage("channel_command_usage"));
			return true;
		}
		Optional<Channel> chOp = ChannelsManager.canaisCache.keySet().stream().filter(k -> {
			if (k.getName().equalsIgnoreCase(args[0]) || k.getCommand().equalsIgnoreCase(args[0])) {
				return true;
			}
			return false;
		}).findAny();

		if (!chOp.isPresent() || chOp.get().isRestrict() && !s.hasPermission(chOp.get().getPermission())) {
			s.sendMessage(getMessage("invalid_channel"));
			return true;
		}

		Channel ch = chOp.get();
		Player p = (Player) s;

		JogadorManager.get(p).setChannel(ch);

		s.sendMessage(ChatColor.translateAlternateColorCodes('&', SimplexChat.getInstance().getConfig().getString("channel_changed").replace("<channelName>", ch.getName()).replace("<channelCommand>", ch.getCommand())));
		return false;
	}

	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		LinkedList<String> playerChs = new LinkedList<>();
		LinkedList<String> result = new LinkedList<>();

		ChannelsManager.canaisCache.keySet().stream().filter(ch -> {
			if (ch.isRestrict()) {
				return sender.hasPermission(ch.getPermission());
			}
			return true;
		}).forEach(ch -> playerChs.add(ch.getName()));

		if (args.length==1) {
			if (!(args[0].equals(""))) {
				playerChs.stream().filter(s -> s.startsWith(args[0])).forEach(result::add);
				return result;
			}
			return playerChs;
		}
		return null;
	}

	public static String getMessage(String path) {
		return ChatColor.translateAlternateColorCodes('&', SimplexChat.getInstance().getConfig().getString(path));
	}
}
