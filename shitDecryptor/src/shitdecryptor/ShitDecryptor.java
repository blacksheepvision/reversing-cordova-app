/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shitdecryptor;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author tesla
 */
public class ShitDecryptor {

    /**
     * @param args the command line arguments
     */
    
    public static String readFile(String fileName) {
        try {
        byte[] data = Files.readAllBytes(Paths.get(fileName));
        return new String(data);
        }
        catch (Exception e) {
            return "";
        }
                 
    }
    
    public static void truncateFile(String filename) {
        try {
            FileChannel outChan = new FileOutputStream(filename, true).getChannel();
            outChan.truncate(0);
            outChan.close();
        }
        catch (Exception e) {
            
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("ShitDecryptor v0.1");
        if (args.length == 1) {
            System.out.println("Decoding ... " + args[0]);
            String raw_data = readFile(args[0]);
            if (raw_data.length() > 0) {
                truncateFile(args[0]);
                byte[] raw_decoded_data = Base64.getDecoder().decode(raw_data);
                try {
                    SecretKeySpec localSecretKeySpec = new SecretKeySpec("secretkeyhere".getBytes("UTF-8"), "AES");
                    Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    IvParameterSpec ivParam = new IvParameterSpec("ivhere".getBytes("UTF-8"));
                    cip.init(2, localSecretKeySpec, (AlgorithmParameterSpec) ivParam);
                    ByteArrayOutputStream raw_decrypted_data = new ByteArrayOutputStream();
                    raw_decrypted_data.write(cip.doFinal(raw_decoded_data));
                    FileOutputStream fos = new FileOutputStream(args[0]);
                    fos.write(raw_decrypted_data.toByteArray());
                    fos.close();
                }
                catch (Exception e) {
                    System.out.print("FATAL!!" + e.getMessage());
                }
            }
            else System.out.println("Could not read file");
        }
        else System.out.println("\n\nUsage: shitDecryptor [filename]");
    }
    
}
