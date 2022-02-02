package cipher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Yeray Sampedro
 */
public class Cipher {
    
    private static byte[] publicKey;
    
    public Cipher() {
        try {
            InputStream is = getClass().getResourceAsStream("Public.key");
            byte[] fileContent = new byte[is.available()];
            is.read(fileContent, 0, is.available());
            publicKey = fileContent;
        } catch (IOException ex) {
            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String cipher(byte[] msg) {
        
        String ret = null;
        try {
            //Creamos la clave publica
            X509EncodedKeySpec ks = new X509EncodedKeySpec(publicKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pub = kf.generatePublic(ks);
            //Instanciamos el cipher en modo RSA/ECB/PKCS1Padding
            javax.crypto.Cipher cipher = javax.crypto.Cipher.
                    getInstance("RSA/ECB/PKCS1Padding");
            // Lo inicializamos con la clave PÚBLICA
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, pub);
            // Ciframos el mensaje
            ret = hexadecimal(cipher.doFinal(msg));        
            
        } catch (NoSuchAlgorithmException | InvalidKeySpecException
                | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException ex) {
            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return ret;
    }
    
    private String hexadecimal(byte[] resumen) {
        String HEX = "";
        for (int i = 0; i < resumen.length; i++) {
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1) {
                HEX += "0";
            }
            HEX += h;
        }
        return HEX.toUpperCase();
    }
    
}