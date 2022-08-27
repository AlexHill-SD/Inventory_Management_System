/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_access_layer;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import models.Role;

/**
 *
 * @author BritishWaldo
 */
public class RoleDB
{
    public List<Role> getAll() throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        
        try
        {
           List<Role> allRoleList = entityManager.createNamedQuery("Role.findAll", Role.class).getResultList();
           return allRoleList;
        }
        finally
        {
            entityManager.close();
        }
    }

    public Role get(int inputRoleID) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        
        try
        {
            Role tempRole = entityManager.find(Role.class, inputRoleID);
            return tempRole;
        }
        finally
        {
            entityManager.close();
        }
    }

    public void insert(Role inputRole) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            entityManager.persist(inputRole);
            
            entityTransaction.commit();
        }
        catch (Exception ex)
        {
            entityTransaction.rollback();
        }
        finally
        {
            entityManager.close();
        }
    }

    public void update(Role inputRole) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            entityManager.merge(inputRole);
            
            entityTransaction.commit();
        }
        catch (Exception ex)
        {
            entityTransaction.rollback();
        }
        finally
        {
            entityManager.close();
        }
    }

    public void delete(Role inputRole) throws Exception 
    {        
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            entityManager.remove(inputRole);
            
            entityTransaction.commit();
        }
        catch (Exception ex)
        {
            entityTransaction.rollback();
        }
        finally
        {
            entityManager.close();
        }
    }
    
    public Role roleIDLookup(String lookupString) throws Exception
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        
        try
        {
            Role tempRole = entityManager.createNamedQuery("Role.findByRoleName", Role.class)
                                            .setParameter("roleName", lookupString)
                                            .getSingleResult();
            
            return tempRole;
        }
        finally
        {
            entityManager.close();
        }
    }
}
