package me.luucx7.simplexchat.core.model.channels;

import me.luucx7.simplexchat.core.api.Local;
import org.bukkit.configuration.file.FileConfiguration;

import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.api.Channel;
import net.md_5.bungee.api.ChatColor;

public class LocalImpl implements Local {

	final String nome = "local";
	String comando = "l";
	String formato;
	boolean broadcast = true;
	boolean restrito = false;
	boolean actionbar = false;
	final boolean habilitado;
	String permissao;
	int raio;
	
	public static FileConfiguration config = SimplexChat.cConfig;
	
	public LocalImpl() {
		this.habilitado = config.getBoolean("local.enable");
		
		if (habilitado) {
			load();
		}
	}
	
	public boolean isEnabled() {
		return this.habilitado;
	}
	
	@Override
	public void load() {
		if (config.isSet("local.broadcast")) this.broadcast = config.getBoolean("local.broadcast");
		if (config.isSet("local.restrict")) this.restrito = config.getBoolean("local.restrict");
		this.raio = config.getInt("local.radius");
		this.permissao = config.getString("local.permission");
		this.formato = ChatColor.translateAlternateColorCodes('&', config.getString("local.format"));
		if (config.isSet("local.actionbar")) this.actionbar = config.getBoolean("local.actionbar");

		if (config.isSet("local.command")) comando = config.getString("local.command");
	}

	@Override
	public String getCommand() {
		return comando;
	}

	@Override
	public String getFormat() {
		return formato;
	}

	@Override
	public void setFormat(String format) {
		this.formato = format;
	}

	@Override
	public boolean isBroadcast() {
		return broadcast;
	}

	@Override
	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}

	@Override
	public boolean isRestrict() {
		return restrito;
	}

	@Override
	public void setRestrict(boolean restrict) {
		this.restrito = restrict;
	}
	
	@Override
	public String getPermission() {
		return permissao;
	}

	@Override
	public void setPermission(String permission) {
		this.permissao = permission;
	}

	@Override
	public int getRadius() {
		return raio;
	}

	@Override
	public void setRadius(int radius) {
		this.raio = radius;
	}

	@Override
	public String getName() {
		return nome;
	}

	@Override
	public boolean useActionbar() {
		return this.actionbar;
	}

	@Override
	public void setActionbar(boolean actionbar) {
		this.actionbar = actionbar;
	}
}
