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
            
            // Concatenar la contraseña del usuario con la clave maestra
            String combinedPassword = password + MASTER_KEY;
            char[] combinedPasswordBytes = combinedPassword.toCharArray();
            
            KeySpec spec = new PBEKeySpec(combinedPasswordBytes, saltBytes, ITERATIONS, KEY_LENGTH); // Usar la contraseña del usuario como clave
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] derivedKey = factory.generateSecret(spec).getEncoded();

            // Convertir la clave derivada a una cadena base64
            String derivedKeyBase64 = Base64.getEncoder().encodeToString(derivedKey);

            // Construir la cadena en el formato deseado
            String result = String.format("pbkdf2:sha256:%d$%s$%s", ITERATIONS, SALT, derivedKeyBase64);
            return result;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            MiarmaCore.LOGGER.severe("Error al encriptar la contraseña: " + e.getMessage());
            return null;
        }
    }
}
