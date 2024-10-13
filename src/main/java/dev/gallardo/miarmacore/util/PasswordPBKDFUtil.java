package dev.gallardo.miarmacore.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import dev.gallardo.miarmacore.MiarmaCore;
import static dev.gallardo.miarmacore.util.Constants.*;

public class PasswordPBKDFUtil {

    private static final String MASTER_KEY = "RRb5rR-8KkVkMY9weht0ZgWF-Vge7LFYYn77d_EfeRI"; // Clave maestra
    private static final String SALT = "MjN72ikE"; // Salt para la derivación de clave
    private static final int ITERATIONS = 150000; // Número de iteraciones
    private static final int KEY_LENGTH = 256; // Longitud de la clave en bits

    public static String encrypt(String password) {
        try {
            byte[] saltBytes = SALT.getBytes();

            String combinedPassword = password + MASTER_KEY;
            char[] combinedPasswordBytes = combinedPassword.toCharArray();
            
            KeySpec spec = new PBEKeySpec(combinedPasswordBytes, saltBytes, ITERATIONS, KEY_LENGTH); // Usar la contraseña del usuario como clave
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] derivedKey = factory.generateSecret(spec).getEncoded();

            String derivedKeyBase64 = Base64.getEncoder().encodeToString(derivedKey);
            return String.format("pbkdf2:sha256:%d$%s$%s", ITERATIONS, SALT, derivedKeyBase64);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            MiarmaCore.LOGGER.severe("Error al encriptar la contraseña: " + e.getMessage());
            return null;
        }
    }

    public static String generateRandomPassword(int size) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~#$&*_+-=.";
        StringBuilder pass = new StringBuilder();
        for(int i = 0; i < size; i++) {
            int random = (int) (Math.random() * chars.length());
            pass.append(chars.charAt(random));
        }
        return pass.toString();
    }
}
