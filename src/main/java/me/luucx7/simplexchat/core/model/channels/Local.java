package me.luucx7.simplexchat.core.model.channels;

import org.bukkit.configuration.file.FileConfiguration;

import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.api.Channel;
import net.md_5.bungee.api.ChatColor;

public class Local implements Channel {

	final String nome = "local";
	String comando = "l";
	String formato;
	boolean broadcast;
	boolean restrito;
	boolean actionbar;
	final boolean habilitado;
	String permissao;
	int raio;
	
	public static FileConfiguration config = SimplexChat.cConfig;
	
	public Local() {
		this.habilitado = config.getBoolean("local.habilitar");
		
		if (habilitado) {
			load();
		}
	}
	
	public boolean isHabilitado() {
		return this.habilitado;
	}
	
	@Override
	public void load() {
		this.broadcast = config.getBoolean("local.broadcast");
		this.restrito = config.getBoolean("local.restrito");
		this.raio = config.getInt("local.raio");
		this.permissao = config.getString("local.permissao");
		this.formato = ChatColor.translateAlternateColorCodes('&', config.getString("local.formato"));
		this.actionbar = config.getBoolean("local.actionbar");

		if (config.isSet("local.comando")) comando = config.getString("local.comando");
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
