package me.luucx7.simplexchat.core.api;

public interface ChatPlayer {
	
	/**
	 * Gets the ChatPlayer name
	 * 
	 * @return String
	 */
	String getName();
	
	/**
	 * Gets the ChatPlayer choosen channel.
	 * 
	 * @return Channel or null if Focus is disabled
	 */
	Channel getChannel();
	
	/**
	 * Set the player focused channel.
	 * This is ignored if Focus is disabled.
	 * 
	 * @param channel
	 */
	void setChannel(Channel channel);
	
	/**
	 * Gets the player choosen color as String, needs to be parsed since
	 * it can be hexadecimal (1.16+) or vanilla ones.
	 * 
	 * @return String
	 */
	String getColor();
	
	/**
	 * Set the player color string. Needs to be parsed since
	 * it can be hexadecimal (1.16+) or vanilla ones.
	 * 
	 * @param color of the player chat
	 */
	void setColor(String color);
}
