package me.luucx7.simplexchat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.api.Channel;
import me.luucx7.simplexchat.core.managers.CanaisManager;
import me.luucx7.simplexchat.core.managers.JogadorManager;
import me.luucx7.simplexchat.core.model.Mensagem;

public class LocalListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent ev) {
		if (ev.isCancelled()) {
			return;
		}
		Channel canal = CanaisManager.local;
		
		if (!CanaisManager.local.isHabilitado()) {
			return;
		}
		ev.setCancelled(true);

		if (SimplexChat.instance.getConfig().getBoolean("modules.focus")) {
			canal = JogadorManager.get(ev.getPlayer()).getChannel();
		}
		String[] gambiarraKKK = {ev.getMessage()};
		Mensagem msg = new Mensagem(ev.getPlayer(), gambiarraKKK, canal);
		msg.preparar().enviar();
	}
}
