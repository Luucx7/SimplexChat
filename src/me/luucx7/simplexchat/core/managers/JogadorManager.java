package me.luucx7.simplexchat.core.managers;

import java.util.ArrayList;
import java.util.Optional;

import org.bukkit.entity.Player;

import me.luucx7.simplexchat.core.model.Jogador;

public class JogadorManager {

	private static final ArrayList<Jogador> chatplayers = new ArrayList<Jogador>();
	
	public static Jogador get(Player player) {
		Optional<Jogador> jogOp = chatplayers.stream().filter(j -> j.getName().equals(player.getName())).findAny();
		if (jogOp.isPresent()) {
			return jogOp.get();
		}
		return new Jogador(player.getName());	
	}
	
	public static void add(Jogador player) {
		chatplayers.add(player);
	}
	
	public static void remove(Player player) {
		try {
			Optional<Jogador> jogOp = chatplayers.stream().filter(j -> j.getName().equals(player.getName())).findAny();
			if (jogOp.isPresent()) {
				chatplayers.remove(jogOp.get());
			}
		} catch(NullPointerException ignored) {}
	}
}
