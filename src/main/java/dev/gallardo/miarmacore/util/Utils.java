package dev.gallardo.miarmacore.util;

import dev.gallardo.miarmacore.MiarmaCore;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	private static final ConfigWrapper cfg = MiarmaCore.getConf();
	
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
       String res = null;
        
       if(matcher.find()) {
    	   String hexColor = matcher.group();
           String minecraftColor = convertHexToMinecraftColor(hexColor);
           res = input.replace(hexColor, minecraftColor);
       } else {
    	   res = input;
       }
       return res;
    }

	public static String convertHexToMinecraftColor(String hexColor) {
        // Extraer los valores R, G y B del formato HEX
        String r1 = hexColor.substring(2, 3);
        String r2 = hexColor.substring(3,4);
        String g1 = hexColor.substring(4, 5);
        String g2 = hexColor.substring(5, 6);
        String b1 = hexColor.substring(6,7);
        String b2 = hexColor.substring(7);

        // Construir el formato de color de Minecraft 
        return "&x&" + r1 + "&" + r2 + "&" + g1 + "&" + g2 + "&" + b1 + "&" + b2;
    }

	public static void copyResourceToFile(String resourceName, String destinationPath) throws IOException {
        ClassLoader classLoader = Utils.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(resourceName);
             FileOutputStream outputStream = new FileOutputStream(destinationPath)) {

            byte[] buffer = new byte[1024];
            int length;
            while (true) {
                assert inputStream != null;
                if ((length = inputStream.read(buffer)) == -1) break;
                outputStream.write(buffer, 0, length);
            }
        }
    }
	
	public static void createLangs(String fileName) {
		try {
			File langs = new File(MiarmaCore.plugin.getDataFolder(), fileName);
			langs.createNewFile();
			copyResourceToFile(fileName, new File(MiarmaCore.plugin.getDataFolder(), fileName).getAbsolutePath());
		} catch (IOException e) {
			MiarmaCore.plugin.getLogger().severe(cfg.getString("language.errors.langs"));
		}
	}

    public static void sendMessage(String message, CommandSender sender, boolean prefix) {
        if(prefix)
            message = message.replace("[P]",cfg.getString("language.prefix"));
        sender.sendMessage(Utils.colorCodeParser(message));
    }

}
