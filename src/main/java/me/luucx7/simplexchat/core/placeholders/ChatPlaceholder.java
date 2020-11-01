package me.luucx7.simplexchat.core.placeholders;

import java.util.regex.Matcher;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.commands.Cores;
import me.luucx7.simplexchat.core.api.Channel;
import me.luucx7.simplexchat.core.managers.JogadorManager;
import net.md_5.bungee.api.ChatColor;

/**
 * This class will be registered through the register-method in the 
 * plugins onEnable-method.
 */
public class ChatPlaceholder extends PlaceholderExpansion {

	private SimplexChat plugin;

	/**
	 * Since we register the expansion inside our own plugin, we
	 * can simply use this method here to get an instance of our
	 * plugin.
	 *
	 * @param plugin
	 *        The instance of our plugin.
	 */
	public ChatPlaceholder(SimplexChat plugin){
		this.plugin = plugin;
	}

	/**
	 * Because this is an internal class,
	 * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
	 * PlaceholderAPI is reloaded
	 *
	 * @return true to persist through reloads
	 */
	@Override
	public boolean persist(){
		return true;
	}

	/**
	 * Because this is a internal class, this check is not needed
	 * and we can simply return {@code true}
	 *
	 * @return Always true since it's an internal class.
	 */
	@Override
	public boolean canRegister(){
		return true;
	}

	/**
	 * The name of the person who created this expansion should go here.
	 * <br>For convienience do we return the author from the plugin.yml
	 * 
	 * @return The name of the author as a String.
	 */
	@Override
	public String getAuthor(){
		return plugin.getDescription().getAuthors().toString();
	}

	/**
	 * The placeholder identifier should go here.
	 * <br>This is what tells PlaceholderAPI to call our onRequest 
	 * method to obtain a value if a placeholder starts with our 
	 * identifier.
	 * <br>The identifier has to be lowercase and can't contain _ or %
	 *
	 * @return The identifier in {@code %<identifier>_<value>%} as String.
	 */
	@Override
	public String getIdentifier(){
		return "chat";
	}

	/**
	 * This is the version of the expansion.
	 * <br>You don't have to use numbers, since it is set as a String.
	 *
	 * For convienience do we return the version from the plugin.yml
	 *
	 * @return The version as a String.
	 */
	@Override
	public String getVersion(){
		return plugin.getDescription().getVersion();
	}

	/**
	 * This is the method called when a placeholder with our identifier 
	 * is found and needs a value.
	 * <br>We specify the value identifier in this method.
	 * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
	 *
	 * @param  player
	 *         A {@link org.bukkit.Player Player}.
	 * @param  identifier
	 *         A String containing the identifier/value.
	 *
	 * @return possibly-null String of the requested identifier.
	 */
	@Override
	public String onPlaceholderRequest(Player player, String identifier){
		if (player==null) {
			return null;
		}
		switch (identifier) {
		case "color":
			String color = JogadorManager.get(player).getColor();
			if (color=="") {
				return "";
			}
			if (validateRGB(color)) {
				return ""+ChatColor.of(color);
			}
			return ""+ChatColor.translateAlternateColorCodes('&', color);
		case "channel":
			Channel canal = JogadorManager.get(player).getChannel();
			return canal.getName().substring(0, 1).toUpperCase()+canal.getName().substring(1);
		default:
			return null;
		}
	}

	// Verify if its a RGB color
	private boolean validateRGB(String input) {
		Matcher matcher = Cores.RGB.matcher(input);

		if (SimplexChat.colorsConfig.getBoolean("enable_rgb") && matcher.matches()) {
			return true;
		}
		return false;
	}
}