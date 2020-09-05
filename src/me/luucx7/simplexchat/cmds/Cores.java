package me.luucx7.simplexchat.cmds;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.luucx7.simplexchat.SimplexChat;
import net.md_5.bungee.api.ChatColor;

public class Cores implements CommandExecutor {
	
    private static HashMap<String, String> colors = new HashMap<>();
    private static final Pattern PATTTERN =Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
    public static final Pattern LEGACY = Pattern.compile("&([0-9a-f]{1})");
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String arg, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage(getString("colors.only_players"));
			return true;
		}
		if (!s.hasPermission("chat.setcor")) {
			s.sendMessage(getString("no_permission"));
			return true;
		}
		if (args.length==0) {
			s.sendMessage(getString("colors.no_arg"));
			return true;
		}
		Player player = (Player) s;
        if (args[0].equalsIgnoreCase("limpar")) {
            colors.remove(player.getName().toLowerCase());
            s.sendMessage(getString("colors.removed"));
            return true;
        }

        if(!validate(args[0])) {
        	s.sendMessage(getString("colors.invalid"));
        	return true;
        }
        colors.put(player.getName().toLowerCase(), args[0]);
        s.sendMessage(PlaceholderAPI.setPlaceholders((Player) s, getString("colors.success")));
		return false;
	}
	
	public static String getPlayerColor(Player player) {
        return colors.getOrDefault(player.getName().toLowerCase(), "");
    }

	private boolean validate(String hexColor) {
        Matcher matcher = PATTTERN.matcher(hexColor);
        Matcher legacyMatch = LEGACY.matcher(hexColor);
        
        if (SimplexChat.useRGB) {
        	return matcher.matches();
        }
        return legacyMatch.matches();
    }

	private static String getString(String path) {
		return ChatColor.translateAlternateColorCodes('&', SimplexChat.instance.getConfig().getString(path));
	}

}