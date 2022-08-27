/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author BritishWaldo
 */
public class PasswordUtilities
{    
    /***********************HASHING METHODS**************************/
    public static String generatePasswordHash(String password) 
                                                throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 10000;
        char[] passwordCharArray = password.toCharArray();
        byte[] saltByteArray = getSalt();

        PBEKeySpec keySpec = new PBEKeySpec(passwordCharArray, saltByteArray, iterations, 64 * 8);
        
        SecretKeyFactory secretFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

        byte[] hash = secretFactory.generateSecret(keySpec).getEncoded();
        return iterations + ":" + toHex(saltByteArray) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        
        byte[] salt = new byte[16];
        
        secureRandom.nextBytes(salt);
        
        return salt;
    }

    private static String toHex(byte[] inputByteArray) throws NoSuchAlgorithmException
    {
        BigInteger byteArrayToBigInt = new BigInteger(1, inputByteArray);
        
        String inputToHex = byteArrayToBigInt.toString(16);

        int paddingLength = (inputByteArray.length * 2) - inputToHex.length();
        
        if(paddingLength > 0)
        {
            return String.format("%0"  + paddingLength + "d", 0) + inputToHex;
        }
        else
        {
            return inputToHex;
        }
    }
    
    /***********************VAlIDATION METHODS**************************/
    public static boolean validatePassword(String originalPassword, String storedPassword) 
                                                    throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] saltPasswordCombo = storedPassword.split(":");
        
        int iterations = Integer.parseInt(saltPasswordCombo[0]);

        byte[] storedSalt = fromHex(saltPasswordCombo[1]);
        byte[] storedPasswordHash = fromHex(saltPasswordCombo[2]);

        PBEKeySpec keySpec = new PBEKeySpec(originalPassword.toCharArray()
                                            , storedSalt, iterations, storedPasswordHash.length * 8);
        
        SecretKeyFactory secretFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        
        byte[] calculatedHash = secretFactory.generateSecret(keySpec).getEncoded();

        for (int i = 0; i < storedPasswordHash.length && i < calculatedHash.length; i++)
        {
        	if (storedPasswordHash[i] != calculatedHash[i])
        	{
        		return false;
        	}
        }
        
        return true;
    }
    
    private static byte[] fromHex(String inputHex) throws NoSuchAlgorithmException
    {
        byte[] convertedByteArray = new byte[inputHex.length() / 2];
        
        for(int i = 0; i < convertedByteArray.length ;i++)
        {
            String extractedHexValue = inputHex.substring(2 * i, 2 * i + 2);
            convertedByteArray[i] = (byte) Integer.parseInt(extractedHexValue, 16);
        }
        
        return convertedByteArray;
    }
}
