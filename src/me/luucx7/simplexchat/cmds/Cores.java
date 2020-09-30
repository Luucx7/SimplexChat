package me.luucx7.simplexchat.cmds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.managers.JogadorManager;
import me.luucx7.simplexchat.core.model.Jogador;
import net.md_5.bungee.api.ChatColor;

public class Cores implements CommandExecutor {
	    
    public static final Pattern RGB = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
    public static final Pattern LEGACY = Pattern.compile("&([0-9a-f]{1})");
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String arg, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage(getString("only_players"));
			return true;
		}
		if (!s.hasPermission("chat.setcor")) {
			s.sendMessage(getString("no_permission"));
			return true;
		}
		if (args.length==0) {
			s.sendMessage(getString("no_arg"));
			return true;
		}
		Player player = (Player) s;
		Jogador jog = JogadorManager.get(player);
		
        if (args[0].equalsIgnoreCase("limpar") || args[0].equalsIgnoreCase("clear")) {
            jog.setColor("");
            s.sendMessage(getString("removed"));
            return true;
        }

        if(!validate(args[0])) {
        	s.sendMessage(getString("invalid"));
        	return true;
        }
        jog.setColor(args[0]);
        s.sendMessage(PlaceholderAPI.setPlaceholders((Player) s, getString("success")));
		return false;
	}

	private boolean validate(String hexColor) {
        Matcher rgbMatcher = RGB.matcher(hexColor);
        Matcher legacyMatcher = LEGACY.matcher(hexColor);
        
        if (SimplexChat.colorsConfig.getBoolean("enable_rgb") && rgbMatcher.matches()) {
        	return true;
        }
        return legacyMatcher.matches();
    }

	private static String getString(String path) {
		return ChatColor.translateAlternateColorCodes('&', SimplexChat.colorsConfig.getString(path));
	}

}