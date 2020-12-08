package me.luucx7.simplexchat.core.api;

public interface Local extends Channel {

    /**
     * If the plugin is handling vanilla chat messages, and then using the local channel.
     *
     * @return local chat enabled
     */
    boolean isEnabled();
}
