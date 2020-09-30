package me.luucx7.simplexchat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.luucx7.simplexchat.core.api.Channel;
import me.luucx7.simplexchat.core.managers.FocusManager;
import me.luucx7.simplexchat.core.managers.JogadorManager;
import me.luucx7.simplexchat.core.model.Jogador;

public class ChatPlayerListener implements Listener {
	
	@EventHandler
	public void join(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		
		Jogador player = JogadorManager.get(p);
		
		if (FocusManager.focusWorlds.containsKey(p.getWorld().getName())) {
			Channel worldCh = FocusManager.focusWorlds.get(p.getWorld().getName());
			player.setChannel(worldCh);
			return;
		}
		JogadorManager.add(player);
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent ev) {
		JogadorManager.remove(ev.getPlayer());
	}
	
	@EventHandler
	public void world(PlayerChangedWorldEvent ev) {
		Player p = ev.getPlayer();
		
		if (FocusManager.focusWorlds.containsKey(p.getWorld().getName())) {
			Channel worldCh = FocusManager.focusWorlds.get(p.getWorld().getName());
			JogadorManager.get(p).setChannel(worldCh);
		}
	}
}
