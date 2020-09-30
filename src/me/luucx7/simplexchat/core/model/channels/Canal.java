package me.luucx7.simplexchat.core.model.channels;

import org.bukkit.configuration.file.FileConfiguration;

import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.api.Channel;
import net.md_5.bungee.api.ChatColor;

public class Canal implements Channel {
	
	final String nome;
	private String comando;
	String formato;
	boolean broadcast;
	boolean restrito;
	String permissao;
	int raio;
	
	public static FileConfiguration config = SimplexChat.cConfig;
	
	public Canal(String nome) {
		this.nome = nome;
		
		load();
	}
	
	@Override
	public void load() {
		this.comando = config.getString(nome+".comando");
		this.broadcast = config.getBoolean(nome+".broadcast");
		this.restrito = config.getBoolean(nome+".restrito");
		this.raio = config.getInt(nome+".raio");
		this.permissao = config.getString(nome+".permissao");
		this.formato = ChatColor.translateAlternateColorCodes('&', config.getString(nome+".formato"));
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
}
