package me.luucx7.simplexchat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.luucx7.simplexchat.Mensagem;
import me.luucx7.simplexchat.SimplexChat;

public class Local implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent ev) {
		if (ev.isCancelled()) {
			return;
		}
		if (!SimplexChat.cConfig.getBoolean("local.habilitar")) {
			return;
		}
		ev.setCancelled(true);
		
		String[] gambiarraKKK = {ev.getMessage()};
		Mensagem msg = new Mensagem(ev.getPlayer(), gambiarraKKK, "local");
		msg.preparar();
		msg.enviar();
	}
}
