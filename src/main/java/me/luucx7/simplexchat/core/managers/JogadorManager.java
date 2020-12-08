package me.luucx7.simplexchat.core.managers;

import java.util.ArrayList;
import java.util.Optional;

import me.luucx7.simplexchat.core.api.ChatPlayer;
import org.bukkit.entity.Player;

import me.luucx7.simplexchat.core.model.Jogador;

public class JogadorManager {

	private static final ArrayList<ChatPlayer> chatplayers = new ArrayList<>();
	
	public static ChatPlayer get(Player player) {
		Optional<ChatPlayer> jogOp = chatplayers.stream().filter(j -> j.getName().equals(player.getName())).findAny();
		if (jogOp.isPresent()) {
			return jogOp.get();
		}
		return new Jogador(player.getName());	
	}
	
	public static void add(ChatPlayer player) {
		chatplayers.add(player);
	}
	
	public static void remove(Player player) {
		try {
			Optional<ChatPlayer> jogOp = chatplayers.stream().filter(j -> j.getName().equals(player.getName())).findAny();
			if (jogOp.isPresent()) {
				chatplayers.remove(jogOp.get());
			}
		} catch(NullPointerException ignored) {}
	}
}
