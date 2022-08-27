/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Role;
import models.User;
import utilities.PasswordUtilities;

/**
 *
 * @author BritishWaldo
 */
public class AccountService
{
    public static String accountLogin(String inputUsername, String inputPassword)
    {
        try
        {
            UserService userConnection = new UserService();
            User userFromDB = userConnection.get(inputUsername);
            String storedPassword = userFromDB.getPassword();
            
            boolean validLogin = PasswordUtilities.validatePassword(inputPassword, storedPassword);
            
            if (validLogin && userFromDB.getActive())
            {
                return userFromDB.getUsername();
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public static boolean userIsAdmin(String inputUsername)
    {
        try
        {
            UserService userConnection = new UserService();
            User userFromDB = userConnection.get(inputUsername);
            Role userRole = userFromDB.getRole();
            
            boolean canManage = userRole.getCanManage();
            
            if (canManage == true)
            {
                return true;
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public void sendPasswordResetEmail(String resetIdentifier, boolean identifierIsEmail, String webINFPath, String baseURL)
    {
        try
        {
            User supposedUser = null;
            
            if (identifierIsEmail)
            {

                    supposedUser = new UserService().getByEmail(resetIdentifier);
                
            }
            else
            {
                    supposedUser = new UserService().get(resetIdentifier);
            }
            
            try
            {
                new UuidService().clearOldUUID(supposedUser.getUsername());
            }
            catch (Exception ex)
            {
                System.out.println("////////////////////////////////////////////");
                System.out.println("There was an error clearing out an old UUID");
                ex.printStackTrace();
            }
            
            new UuidService().insert(supposedUser.getUsername());
            
            String resetLink = baseURL + "?uuid=" + new UuidService().getUUID(supposedUser.getUsername());
            String destinationEmail = supposedUser.getEmail();
            String emailSubjectLine = "Home Inventory Password Reset";
            String template = webINFPath + "/email_templates/resetPasswordTemplate.html";
            
            HashMap<String, String> templateVariableMap = new HashMap<>();
            templateVariableMap.put("passwordResetLink", resetLink);
            templateVariableMap.put("username", supposedUser.getUsername());
            
            EmailProviderService.sendMail(destinationEmail, emailSubjectLine, template, templateVariableMap);
        }
        catch (Exception ex)
        {
            //Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public boolean resetPassword(String hashedUUID, String newPassword)
    {
        UserService userConnection = new UserService();
        boolean resetSuccess = false;
        
        try
        {
            String foundUsername = new UuidService().getUsername(hashedUUID);
            
            if (foundUsername != null)
            {                
                userConnection.updatePassword(foundUsername, newPassword);
                new UuidService().delete(hashedUUID);
                return true;
            }
        }
        catch (Exception ex)
        {
            //Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resetSuccess;
    }
    
    public void sendAccountActivationEmail(String emailAddress, String webINFPath, String baseURL)
    {
        try
        {
            User supposedUser = null;

            supposedUser = new UserService().getByEmail(emailAddress);
            
            try
            {
                new UuidService().clearOldUUID(supposedUser.getUsername());
            }
            catch (Exception ex)
            {
                System.out.println("////////////////////////////////////////////");
                System.out.println("There was an error clearing out an old UUID");
                ex.printStackTrace();
            }

            new UuidService().insert(supposedUser.getUsername());
            
            String activationLink = baseURL + "?uuid=" + new UuidService().getUUID(supposedUser.getUsername());
            String destinationEmail = supposedUser.getEmail();
            String emailSubjectLine = "Home Inventory Account Activation";
            String template = webINFPath + "/email_templates/activationEmailTemplate.html";
            
            HashMap<String, String> templateVariableMap = new HashMap<>();
            templateVariableMap.put("activationLink", activationLink);
            templateVariableMap.put("username", supposedUser.getUsername());
            
            EmailProviderService.sendMail(destinationEmail, emailSubjectLine, template, templateVariableMap);
        }
        catch (Exception ex)
        {
            //Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public boolean activateAccount(String hashedUUID)
    {
        UserService userConnection = new UserService();
        boolean activationSuccess = false;
        
        try
        {
            String foundUsername = new UuidService().getUsername(hashedUUID);
            
            System.out.println(foundUsername);
            
            if (foundUsername != null)
            {                
                User userToActivate = userConnection.get(foundUsername);
                
                System.out.println(userToActivate);
                
                userToActivate.setActive(true);
                
                userConnection.updateActiveStatus(userToActivate);
                
                new UuidService().delete(hashedUUID);
                
                activationSuccess = true;
                
                return activationSuccess;
            }
        }
        catch (Exception ex)
        {
            //Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return activationSuccess;
    }
}
