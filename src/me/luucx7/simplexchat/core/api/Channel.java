package me.luucx7.simplexchat.core.api;

public interface Channel {
	
	/**
	 * Loads the channel configuration from channels.yml
	 */
	public void load();
	
	/**
	 * Get the command 
	 * 
	 * @return
	 */
	public String getCommand();

	public String getFormat();

	public void setFormat(String format);

	public boolean isBroadcast();

	public void setBroadcast(boolean broadcast);

	public boolean isRestrict();

	public void setRestrict(boolean restrict);

	public String getPermission();

	public void setPermission(String permission);

	public int getRadius();

	public void setRadius(int radius);

	public String getName();
	
	public boolean useActionbar();
	
	public void setActionbar(boolean actionbar);
}
