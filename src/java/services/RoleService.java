/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import data_access_layer.RoleDB;
import java.util.List;
import models.Role;

/**
 *
 * @author BritishWaldo
 */
public class RoleService
{
    public Role get(int inputRoleID) throws Exception 
    {
        RoleDB roleConnection = new RoleDB();
        Role tempRole = roleConnection.get(inputRoleID);
        return tempRole;
    }
    
    public List<Role> getAll() throws Exception 
    {
        RoleDB roleConnection = new RoleDB();
        List<Role> allRolesList = roleConnection.getAll();
        return allRolesList;
    }
    
    public void insert(int inputRoleID, String inputRoleName, boolean inputCanManage) throws Exception 
    {
        RoleDB roleConnection = new RoleDB();
        Role tempRole = new Role(inputRoleID, inputRoleName, inputCanManage);
        roleConnection.insert(tempRole);
    }
    
    public void update(int inputRoleID, String inputRoleName) throws Exception 
    {
        RoleDB roleConnection = new RoleDB();
        Role tempRole = this.get(inputRoleID);
        
        if (!tempRole.getRoleName().equals(inputRoleName))
        {
            tempRole.setRoleName(inputRoleName);
        }
        
        roleConnection.update(tempRole);
    }
    
    public void delete(int inputRoleID) throws Exception 
    {
        RoleDB roleConnection = new RoleDB();
        Role tempRole = new Role(inputRoleID);
        roleConnection.update(tempRole);
    }
    
    public int roleIDLookup(String lookupString) throws Exception
    {
        RoleDB roleConnection = new RoleDB();
        
        Role tempRole = roleConnection.roleIDLookup(lookupString);
        
        return tempRole.getRoleId();
    }    
}
