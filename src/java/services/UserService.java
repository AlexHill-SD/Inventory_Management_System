/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import data_access_layer.RoleDB;
import data_access_layer.UserDB;
import java.util.List;
import models.Role;
import models.User;
import utilities.PasswordUtilities;

/**
 *
 * @author BritishWaldo
 */
public class UserService
{
    private final int BASICROLE = 2;
    
    public User get(String inputUsername) throws Exception 
    {
        UserDB userConnection = new UserDB();
        User tempUser = userConnection.get(inputUsername);
        return tempUser;
    }
    
    public User getByEmail(String inputEmail) throws Exception 
    {
        UserDB userConnection = new UserDB();
        User tempUser = userConnection.getByEmail(inputEmail);
        return tempUser;
    }
    
    public List<User> getAll() throws Exception 
    {
        UserDB userConnection = new UserDB();
        List<User> allUsersList = userConnection.getAll();
        return allUsersList;
    }
    
    public List<User> getAllSorted(String columnName) throws Exception
    {
        UserDB userConnection = new UserDB();
        List<User> allUsersListSorted = userConnection.getAllSorted(columnName);
        return allUsersListSorted;
    }
    
    public void insert(String newUsername, String newPassword, String newEmail, String newFirstName, String newLastName, boolean newIsActive, int newUserRoleID) throws Exception 
    {
        UserDB userConnection = new UserDB();
        
        String generatedPasswordHash = PasswordUtilities.generatePasswordHash(newPassword);
        
        User tempUser = new User(newUsername, generatedPasswordHash, newEmail, newFirstName, newLastName, newIsActive);
        
        RoleDB roleConnection = new RoleDB();
        Role tempRole = roleConnection.get(newUserRoleID);
        
        tempUser.setRole(tempRole);
        userConnection.insert(tempUser);
    }
    
    public void insertNewUser(String newUsername, String newPassword, String newEmail, String newFirstName, String newLastName) throws Exception 
    {
        UserDB userConnection = new UserDB();
        
        String generatedPasswordHash = PasswordUtilities.generatePasswordHash(newPassword);
        
        User tempUser = new User(newUsername, generatedPasswordHash, newEmail, newFirstName, newLastName, false);
        
        RoleService roleConnection = new RoleService();
        Role tempRole = roleConnection.get(BASICROLE);
        
        tempUser.setRole(tempRole);
        userConnection.insert(tempUser);
    }
    
    public void update(String originalUsername, String newUsername, String newEmail, String newFirstName, String newLastName, boolean newIsActive, int newUserRoleID) throws Exception 
    {
        UserDB userConnection = new UserDB();
        User tempUser = this.get(originalUsername);
        
        if (!tempUser.getUsername().equals(newUsername))
        {
            tempUser.setUsername(newUsername);
        }
        
        if (!tempUser.getEmail().equals(newEmail))
        {
            tempUser.setEmail(newEmail);
        }
        
        if (!tempUser.getFirstName().equals(newFirstName))
        {
            tempUser.setFirstName(newFirstName);
        }
        
        if (!tempUser.getLastName().equals(newLastName))
        {
            tempUser.setLastName(newLastName);
        }
        
        if (tempUser.getActive() != newIsActive)
        {
            tempUser.setActive(newIsActive);
        }
        
        if (tempUser.getRole().getRoleId() != newUserRoleID)
        {
            RoleDB roleConnection = new RoleDB();
            Role tempRole = roleConnection.get(newUserRoleID);
            tempUser.setRole(tempRole);
        }
        
        userConnection.update(originalUsername, tempUser);
    }
    
    public void update(String originalUsername, String newUsername, String newPassword, String newEmail, String newFirstName, String newLastName, boolean newIsActive, int newUserRoleID) throws Exception 
    {
        UserDB userConnection = new UserDB();
        User tempUser = this.get(originalUsername);
        
        String generatedPasswordHash = PasswordUtilities.generatePasswordHash(newPassword);
        
        if (!tempUser.getUsername().equals(newUsername))
        {
            tempUser.setUsername(newUsername);
        }
        
        if (!tempUser.getEmail().equals(newEmail))
        {
            tempUser.setEmail(newEmail);
        }
        
        if (!tempUser.getPassword().equals(generatedPasswordHash))
        {
            tempUser.setPassword(generatedPasswordHash);
        }
        
        if (!tempUser.getFirstName().equals(newFirstName))
        {
            tempUser.setFirstName(newFirstName);
        }
        
        if (!tempUser.getLastName().equals(newLastName))
        {
            tempUser.setLastName(newLastName);
        }
        
        if (tempUser.getActive() != newIsActive)
        {
            tempUser.setActive(newIsActive);
        }
        
        if (tempUser.getRole().getRoleId() != newUserRoleID)
        {
            RoleDB roleConnection = new RoleDB();
            Role tempRole = roleConnection.get(newUserRoleID);
            tempUser.setRole(tempRole);
        }
        
        userConnection.update(originalUsername, tempUser);
    }
    
    public void update(User userToUpdate) throws Exception 
    {
        UserDB userConnection = new UserDB();

        userConnection.update(userToUpdate.getUsername(), userToUpdate);
    }
    
    public void updateActiveStatus(User userToUpdate) throws Exception 
    {
        UserDB userConnection = new UserDB();

        userConnection.updateActiveStatus(userToUpdate);
    }

    public void updatePassword(String username, String newPassword) throws Exception 
    {
        UserDB userConnection = new UserDB();
        User tempUser = this.get(username);
        
        String generatedPasswordHash = PasswordUtilities.generatePasswordHash(newPassword);
        tempUser.setPassword(generatedPasswordHash);
        
        userConnection.updatePassword(tempUser);
    }
    
    public void delete(String inputUsername) throws Exception 
    {
        UserDB userConnection = new UserDB();
        User tempUser = new User(inputUsername);
        userConnection.delete(tempUser);
    }    
}
