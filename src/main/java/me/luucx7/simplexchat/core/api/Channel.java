package me.luucx7.simplexchat.core.api;

public interface Channel {

    /**
     * Loads the channel configuration from channels.yml
     */
    void load();

    /**
     * Get the command
     *
     * @return the command name
     */
    String getCommand();

    String getFormat();

    void setFormat(String format);

    boolean isBroadcast();

    void setBroadcast(boolean broadcast);

    boolean isRestrict();

    void setRestrict(boolean restrict);

    String getPermission();

    void setPermission(String permission);

    int getRadius();

    void setRadius(int radius);

    String getName();

    boolean useActionbar();

    void setActionbar(boolean actionbar);
}
