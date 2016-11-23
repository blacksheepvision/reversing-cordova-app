/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shitencryptor;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author tesla
 */
public class ShitEncryptor {

    /**
     * @param args the command line arguments
     */
    
    public static byte[] readFile(String fileName) {
        try {
        byte[] data = Files.readAllBytes(Paths.get(fileName));
        return data;
        }
        catch (Exception e) {
            return null;
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
        System.out.println("ShitEncryptor v0.1");
        if (args.length == 1) {
            System.out.println("Encoding ... " + args[0]);
            byte[] raw_data = readFile(args[0]);
            if (raw_data != null) {
                truncateFile(args[0]);
                try {
                    SecretKeySpec localSecretKeySpec = new SecretKeySpec("secretkeyhere".getBytes("UTF-8"), "AES");
                    Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    IvParameterSpec ivParam = new IvParameterSpec("ivhere".getBytes("UTF-8"));
                    cip.init(Cipher.ENCRYPT_MODE, localSecretKeySpec, (AlgorithmParameterSpec) ivParam);
                    raw_data = cip.doFinal(raw_data);
                    raw_data = Base64.getEncoder().encode(raw_data);
                    FileOutputStream fos = new FileOutputStream(args[0]);
                    fos.write(raw_data);
                    fos.close();
                }
                catch (Exception e) {
                    System.out.print("FATAL!!" + e.getMessage());
                }
            }
            else System.out.println("Could not read file");
        }
        else System.out.println("\n\nUsage: shitEncryptor [filename]");
    }

    
}
