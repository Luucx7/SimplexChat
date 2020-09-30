package me.luucx7.simplexchat.core.model;

import me.luucx7.simplexchat.core.api.Channel;
import me.luucx7.simplexchat.core.api.ChatPlayer;
import me.luucx7.simplexchat.core.managers.CanaisManager;

public class Jogador implements ChatPlayer {
	
	final String name;
	Channel canal;
	String color;
	
	public Jogador(String name) {
		this.name = name;
		this.color = "";
		this.canal = CanaisManager.local;
	}
	public Jogador(String name, Channel canal) {
		this.name = name;
		this.canal = canal;
		this.color = "";
	}
	public Jogador(String name, String color) {
		this.name = name;
		this.color = color;
		this.canal = CanaisManager.local;
	}
	public Jogador(String name, Channel channel, String color) {
		this.name = name;
		this.color = color;
		this.canal = channel;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public Channel getChannel() {
		return this.canal;
	}
	@Override
	public void setChannel(Channel canal) {
		this.canal = canal;
	}
	
	@Override
	public String getColor() {
		return this.color;
	}
	@Override
	public void setColor(String color) {
		this.color = color;
	}
}
