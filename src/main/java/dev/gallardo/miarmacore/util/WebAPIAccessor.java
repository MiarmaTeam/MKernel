package dev.gallardo.miarmacore.util;

import dev.gallardo.miarmacore.MiarmaCore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class WebAPIAccessor {
    private static ConfigWrapper config = MiarmaCore.getConf();
    private static final String API_BASE = config.getString("config.API_BASE");

	public static boolean register(String username, String password, String role) {
        String json = "{\"username\": \"" + username + "\", \"password\": \"" + PasswordPBKDF.encrypt((String)password) + "\" , \"rol\": \"" + role + "\"}";
        try {
            String output;
            URL url = new URL(API_BASE);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((output = br.readLine()) != null) {
                MiarmaCore.getPlugin().getLogger().log(Level.INFO, output);
            }
            conn.disconnect();
            return true;
        }
        catch (Exception e) {
            MiarmaCore.getPlugin().getLogger().log(Level.SEVERE, e.getMessage());
            return false;
        }
    }
}
