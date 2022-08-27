/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_access_layer;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import models.Category;
import models.Item;
import models.User;

/**
 *
 * @author BritishWaldo
 */
public class ItemDB
{
    public List<Item> getAll() throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        
        try
        {
           List<Item> allItemList = entityManager.createNamedQuery("Item.findAll", Item.class).getResultList();
           return allItemList;
        }
        finally
        {
            entityManager.close();
        }
    }
    
    public List<Item> getAllSortedForUser(String username, String columnName)
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        List<Item> allItemListSorted = null;
        
        try
        {
            entityTransaction.begin();
            
            Query customQuery = entityManager.createQuery("SELECT i FROM Item i WHERE i.owner.username = '" + username + "' ORDER BY i." + columnName);

            allItemListSorted =  customQuery.getResultList();
            
            return allItemListSorted;
        }
        finally
        {
            entityManager.close();
        }
    }

    public Item get(int inputItemID) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        
        try
        {
            Item tempItem = entityManager.find(Item.class, inputItemID);
            return tempItem;
        }
        finally
        {
            entityManager.close();
        }
    }

    public void insert(Item inputItem) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            Category itemCategory = inputItem.getCategory();
            
            itemCategory.getItemList().add(inputItem);
            
            User itemOwner = inputItem.getOwner();
            
            itemOwner.getItemList().add(inputItem);
            
            entityTransaction.begin();
            
            entityManager.persist(inputItem);
            
            entityManager.merge(itemCategory);
            entityManager.merge(itemOwner);
            
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

    public void update(Item inputItem) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            entityManager.merge(inputItem);
            
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

    public void delete(Item inputItem) throws Exception 
    {        
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            Item confirmedItem = entityManager.merge(this.get(inputItem.getItemID()));
            
            Category itemCategory = confirmedItem.getCategory();
            itemCategory.getItemList().remove(confirmedItem);
            
            User itemOwner = confirmedItem.getOwner();
            itemOwner.getItemList().remove(confirmedItem);
            
            entityManager.remove(confirmedItem);
            
            entityManager.merge(itemCategory);
            entityManager.merge(itemOwner);
            
            entityTransaction.commit();
        }
        catch (Exception ex)
        {
            entityTransaction.rollback();
            ex.printStackTrace();
        }
        finally
        {
            entityManager.close();
        }
    }
    
    public int getNewItemID() throws Exception
    {
        int newID = -1;
        
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            Query customQuery = entityManager.createQuery("SELECT MAX(i.itemID) FROM Item i");
            
            newID = (Integer) customQuery.getSingleResult();
            
            newID++;
            
            return newID;
            
        }
        finally
        {
            entityManager.close();
        }
    }
}
