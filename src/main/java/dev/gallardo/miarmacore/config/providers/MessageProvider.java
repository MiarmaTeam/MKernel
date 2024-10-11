package dev.gallardo.miarmacore.config.providers;

public class MessageProvider {
    private static MessageProvider instance;
    private MessageProvider() {}
    public static MessageProvider getInstance() {
        if (instance == null) {
            instance = new MessageProvider();
        }
        return instance;
    }


}
