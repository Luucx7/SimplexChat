package me.luucx7.simplexchat;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cores implements CommandExecutor {
	
    private static HashMap<String, String> colors = new HashMap<>();
    private static final Pattern PATTTERN =Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String arg, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage("§cDisponível apenas para jogadores!");
			return true;
		}
		if (args.length==0) {
			s.sendMessage("§cDigite uma cor! /chatcor #<cor>");
			return true;
		}
		Player player = (Player) s;
        if (args[0].equalsIgnoreCase("limpar")) {
            colors.remove(player.getName().toLowerCase());
            s.sendMessage("§aVocê removeu sua cor de chat!");
            return true;
        }

        if(!validate(args[0])) {
        	s.sendMessage("§cDigite uma cor hexadecimal válida! /chatcor #<cor>");
        	return true;
        }
        colors.put(player.getName().toLowerCase(), args[0]);
        player.sendMessage("§aAs cores do seu chat foram alteradas com sucesso!");
		return false;
	}
	
	public static String getPlayerColor(Player player) {
        return colors.getOrDefault(player.getName().toLowerCase(), "");
    }

	private boolean validate(String hexColor) {
        Matcher matcher = PATTTERN.matcher(hexColor);
        return matcher.matches();
    }


}