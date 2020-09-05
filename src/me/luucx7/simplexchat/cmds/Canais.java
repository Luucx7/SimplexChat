package me.luucx7.simplexchat.cmds;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.luucx7.simplexchat.Mensagem;
import me.luucx7.simplexchat.SimplexChat;
import me.luucx7.simplexchat.core.CanaisManager;
import me.luucx7.simplexchat.core.model.Canal;

public class Canais extends Command {

	String comando;
	
	public Canais(String name) {
		super(name);
	}

	@Override
	public boolean execute(CommandSender s, String comando, String[] args) {
		Optional<Canal> canalOp = CanaisManager.canaisCache.keySet().stream().filter(canal -> canal.getComando().equalsIgnoreCase(comando)).findFirst();
		if (!canalOp.isPresent()) {
			return true;
		}
		
		Canal canal = canalOp.get();
		
		if (canal.isRestrito()) {
			if (!s.hasPermission(canal.getPermissao())) {
				s.sendMessage("§cVocê não tem permissão!");
				return true;
			}
		}
		
		if (args.length==0) {
			s.sendMessage("§cUso: /"+comando.toLowerCase()+" <mensagem>");
			return true;
		}
		
		Bukkit.getScheduler().runTaskAsynchronously(SimplexChat.instance, () -> {
			Mensagem msg = new Mensagem((Player) s, args, canal.getNome());
			msg.preparar();
			msg.enviar();
		});
		return false;
	}

}
