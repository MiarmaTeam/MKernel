package dev.gallardo.miarmacore.common;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.PasswordPBKDFUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

import static dev.gallardo.miarmacore.util.Constants.*;

public class WebAPIAccessor {
    private static final String API_BASE = MiarmaCore.CONFIG.getString("API_BASE");

	public static boolean register(String username, String password, String role) {
        String json = "{\"username\": \"" + username + "\", \"password\": \"" + PasswordPBKDFUtil.encrypt(password) + "\" , \"rol\": \"" + role + "\"}";
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
                MiarmaCore.PLUGIN.getLogger().log(Level.INFO, output);
            }
            conn.disconnect();
            return true;
        }
        catch (Exception e) {
            MiarmaCore.PLUGIN.getLogger().log(Level.SEVERE, e.getMessage());
            return false;
        }
    }
}
