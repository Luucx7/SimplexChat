package me.luucx7.simplexchat.core.model;

import org.bukkit.configuration.file.FileConfiguration;

import me.luucx7.simplexchat.SimplexChat;
import net.md_5.bungee.api.ChatColor;

public class Canal {
	
	final String nome;
	String comando;
	String formato;
	boolean broadcast;
	boolean restrito;
	String permissao;
	int raio;
	
	public static FileConfiguration config = SimplexChat.instance.getConfig();
	
	public Canal(String nome) {
		this.nome = nome;
		
		load();
	}
	
	public void load() {
		this.comando = config.getString(nome+".comando");
		this.broadcast = config.getBoolean(nome+".broadcast");
		this.restrito = config.getBoolean(nome+".restrito");
		this.raio = config.getInt(nome+".raio");
		this.permissao = config.getString(nome+".permissao");
		this.formato = ChatColor.translateAlternateColorCodes('&', config.getString(nome+".formato"));
	}
	
	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public boolean isBroadcast() {
		return broadcast;
	}

	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}

	public boolean isRestrito() {
		return restrito;
	}

	public void setRestrito(boolean restrito) {
		this.restrito = restrito;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	public int getRaio() {
		return raio;
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}

	public String getNome() {
		return nome;
	}
}
