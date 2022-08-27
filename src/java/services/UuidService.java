/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import data_access_layer.UuidDB;
import java.util.UUID;
import utilities.PasswordUtilities;

/**
 *
 * @author BritishWaldo
 */
public class UuidService
{
    public String getUsername(String hashedUUID) throws Exception 
    {
        UuidDB uuidConnection = new UuidDB();
        String tempUsername = uuidConnection.getUsername(hashedUUID);
        System.out.println(tempUsername);
        return tempUsername;
    }
    
    public String getUUID(String username) throws Exception 
    {
        UuidDB uuidConnection = new UuidDB();
        String tempUUID = uuidConnection.getUUID(username);
        return tempUUID;
    }
    
    public void insert(String username) throws Exception 
    {
        UuidDB uuidConnection = new UuidDB();
        
        String rawUUID = UUID.randomUUID().toString();
        String hashedUUID = PasswordUtilities.generatePasswordHash(rawUUID);
            
        uuidConnection.insert(username, hashedUUID);
    }
    
    public void delete(String hashedUUID) throws Exception 
    {
        UuidDB uuidConnection = new UuidDB();
        
        uuidConnection.delete(hashedUUID);
    }

    public void clearOldUUID(String username) throws Exception 
    {
        UuidDB uuidConnection = new UuidDB();
        
        String uuidToDelete = uuidConnection.getUUID(username);
        
        this.delete(uuidToDelete);
    }
}
