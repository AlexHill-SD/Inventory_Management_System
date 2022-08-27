/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_access_layer;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 *
 * @author BritishWaldo
 */
public class UuidDB
{
    public String getUsername(String hashedUUID) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        String username = null;
        
        try
        {            
            username = (String) entityManager.createNativeQuery("Select username"
                                                                + " from uuid_map"
                                                                + " where uuid = ?hashedUUID"
                                                                )
                                                .setParameter("hashedUUID", hashedUUID)
                                                .getSingleResult();
            
            System.out.println("////");
            System.out.println(username);
        } 
        catch (Exception ex)
        {
            //ex.printStackTrace();
        }
        finally {
            entityManager.close();
        }
        
        return username;
    }
    
    public String getFullHashedUUID(String username) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        String hashedUUID = null;

        try
        {            
            hashedUUID =  (String) entityManager.createNativeQuery("Select concat(iterations, \":\", salt, \":\", uuid)"
                                                                + " from uuid_map"
                                                                + " where username = ?username"
                                                                )
                                                .setParameter("username", username)
                                                .getSingleResult();
        } 
        catch (Exception ex)
        {
            
        }
        finally {
            entityManager.close();
        }
        
        return hashedUUID;
    }
    
    public String getUUID(String username) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        String hashedUUID = null;

        try
        {            
            hashedUUID =  (String) entityManager.createNativeQuery("Select uuid"
                                                                + " from uuid_map"
                                                                + " where username = ?username"
                                                                )
                                                .setParameter("username", username)
                                                .getSingleResult();

        } 
        catch (Exception ex)
        {
            
        }
        finally 
        {
            entityManager.close();
        }
        
        return hashedUUID;
    }
    
    public void insert(String username, String hashedUUID)
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            String iterations = hashedUUID.substring(0, hashedUUID.indexOf(':'));
            hashedUUID = hashedUUID.substring(hashedUUID.indexOf(':') + 1);
            String salt = hashedUUID.substring(0, hashedUUID.indexOf(':'));
            hashedUUID = hashedUUID.substring(hashedUUID.indexOf(':') + 1);
            String uuid = hashedUUID;
            
            entityTransaction.begin();
            
            entityManager.createNativeQuery("INSERT INTO uuid_map"
                                            + " (username, iterations, salt, uuid)"
                                            + " VALUES (?username, ?iterations, ?salt, ?uuid)"
                                            )
                        .setParameter("username", username)
                        .setParameter("iterations", iterations)
                        .setParameter("salt", salt)
                        .setParameter("uuid", uuid)
                        .executeUpdate();
            
            entityTransaction.commit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            entityTransaction.rollback();
        }
        finally
        {
            entityManager.close();
        }
    }
    
    public void delete(String hashedUUID)
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            entityManager.createNativeQuery("DELETE FROM uuid_map"
                                            + " WHERE uuid = ?hasheduuid"
                                            )
                         .setParameter("hasheduuid", hashedUUID)
                         .executeUpdate();
            
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
}
