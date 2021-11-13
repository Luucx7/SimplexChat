package me.luucx7.simplexchat.core.managers;

import me.luucx7.simplexchat.SimplexChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageManager {
    private static final Map<Player, String> lastMessage = new HashMap<>();

    public static void setLastMessage(Player p, String msg) {
        lastMessage.put(p, msg.toLowerCase());
    }

    public static boolean isFlood(Player p, String msg) {
        if (!lastMessage.containsKey(p)) return false;
        return lastMessage.get(p).equalsIgnoreCase(msg);
    }

    public static boolean isSpam(String[] args) {
        List<String> check = new ArrayList<>();
        int limit = SimplexChat.instance.getConfig().getInt("spam_limit");

        for (String msg : args) {
            if (msg.length() > limit) check.add(msg.toLowerCase());
        }

        if (check.isEmpty()) return false;

        int count = 0;
        for (String msg : check) {
            System.out.println(msg);

            for (char c : msg.toCharArray()) {
                count = 0;

                for (int i = 0; i < msg.length(); i++) {
                    if (c == msg.toCharArray()[i]) {
                        if (i == 0 || c == msg.toCharArray()[i - 1]) count++;
                        else count = 1;
                    }
                }

                if (count > limit) {
                    break;
                }
            }
        }

        return count > limit;
    }

    public static String[] formatTitle(String[] args) {
        char[] msgArray = String.join(" ", args).toLowerCase().toCharArray();
        msgArray[0] = String.valueOf(msgArray[0]).toUpperCase().charAt(0);

        return String.valueOf(msgArray).split(" ");
    }
}
