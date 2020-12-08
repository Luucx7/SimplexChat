package me.luucx7.simplexchat.core.api;

public interface Channel {

    /**
     * Loads the channel configuration from channels.yml
     */
    void load();

    /**
     * Get the channel command
     *
     * @return the command name
     */
    String getCommand();

    /**
     * Gets the channel base format
     *
     * @return Channel format
     */
    String getFormat();

    /**
     * Defines a new channel format
     *
     * @param format
     */
    void setFormat(String format);

    /**
     * If the channel message will ignore player distances
     *
     * @return Broadcast or not
     */
    boolean isBroadcast();

    /**
     * Defines if the channel will ignore player distances
     *
     * @param broadcast
     */
    void setBroadcast(boolean broadcast);

    /**
     * If players needs a permission to use and see this channel
     *
     * @return
     */
    boolean isRestrict();

    /**
     * Defines if the channel will have a permission for using it
     *
     * @param restrict
     */
    void setRestrict(boolean restrict);

    /**
     * Returns the channel permission, or null if not set
     *
     * @return Permission, or null
     */
    String getPermission();

    /**
     * Defines a new channel permission. Needs isBroadcast() to be true to be used
     *
     * @param permission
     */
    void setPermission(String permission);

    /**
     * Block radius if the channel is not in broadcast mode. Players will need to be within this radius to see a message.
     *
     * @return Int radius
     */
    int getRadius();

    /**
     * Defines a new radius to the channel, requires isBroadcast to be false.
     *
     * @param radius
     */
    void setRadius(int radius);

    /**
     * Returns the channel name. The name is the channel key in channels.yml
     *
     * @return String name
     */
    String getName();

    /**
     * If an actionbar message should be sent when typing in this channeç
     *
     * @return Boolean use actionbar
     */
    boolean useActionbar();

    /**
     * Defines if the channel will send actionbar messages when typing on it
     *
     * @param actionbar boolean
     */
    void setActionbar(boolean actionbar);
}
