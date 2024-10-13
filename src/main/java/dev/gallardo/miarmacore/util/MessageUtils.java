package dev.gallardo.miarmacore.util;

import dev.gallardo.miarmacore.config.providers.MessageProvider;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {
    public static String placeholderParser(String message, List<String> placeholders, List<String> values) {
        int i = 0;
        for(String p:placeholders) {
            if(message.contains(p)) {
                message = message.replace(p, values.get(i));
                i++;
            }
        }
        return message;
    }

    public static String colorCodeParser(String message) {
        message = MojangHEXParser(message).replace('&', '§');
        return message;
    }

    public static String MojangHEXParser(String input) {
        String hex = "&#[0-9A-Fa-f]{6}";
        Pattern pattern = Pattern.compile(hex);
        Matcher matcher = pattern.matcher(input);

        if(matcher.find()) {
            String hexColor = matcher.group();
            String minecraftColor = convertHexToMinecraftColor(hexColor);
            input = input.replace(hexColor, minecraftColor);
        }

        return input;
    }

    public static String convertHexToMinecraftColor(String hexColor) {
        String r1 = hexColor.substring(2, 3);
        String r2 = hexColor.substring(3,4);
        String g1 = hexColor.substring(4, 5);
        String g2 = hexColor.substring(5, 6);
        String b1 = hexColor.substring(6,7);
        String b2 = hexColor.substring(7);
        return "&x&" + r1 + "&" + r2 + "&" + g1 + "&" + g2 + "&" + b1 + "&" + b2;
    }

    public static String formatMessageNoColors(String message, boolean prefix){
        if(prefix)
            message = message.replace("[P]", MessageProvider.getPrefix());
        return message;
    }

    public static String formatMessageNoColors(String message, boolean prefix, boolean placeholders, List<String> phs, List<String> values){
        if(prefix)
            message = message.replace("[P]",MessageProvider.getPrefix());
        if(placeholders)
            message = placeholderParser(message, phs, values);
        return message;
    }

    public static String formatMessage(String message, boolean prefix){
        if(prefix)
            message = message.replace("[P]",MessageProvider.getPrefix());
        return colorCodeParser(message);
    }

    public static String formatMessage(String message, boolean prefix, boolean placeholders, List<String> phs, List<String> values){
        if(placeholders)
            message = placeholderParser(message, phs, values);
        return formatMessage(message, prefix);
    }

    public static String formatMessageNoPrefix(String message) {
        return colorCodeParser(message.replace("[P]", ""));
    }

    public static String removePrefix(String message) {
        return message.replace("[P]", "");
    }

    public static void sendMessage(String message, CommandSender sender, boolean prefix) {
        sender.sendMessage(formatMessage(message, prefix));
    }

    public static void sendMessage(String message, CommandSender sender, boolean prefix, boolean placeholders,
                                   List<String> phs, List<String> values) {
        sender.sendMessage(formatMessage(message, prefix, placeholders, phs, values));
    }
}
