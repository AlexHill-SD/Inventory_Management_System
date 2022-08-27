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

/**
 *
 * @author BritishWaldo
 */
public class CategoryDB
{
    public List<Category> getAll() throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        
        try
        {
           List<Category> allCategoryList = entityManager.createNamedQuery("Category.findAll", Category.class).getResultList();
           return allCategoryList;
        }
        finally
        {
            entityManager.close();
        }
    }
    
    public List<Category> getAllSorted(String columnName) throws Exception
    {        
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        List<Category> allCategoryListSorted = null;
        
        try
        {
            entityTransaction.begin();

            Query customQuery = entityManager.createQuery("SELECT c FROM Category c ORDER BY c." + columnName);
            
            allCategoryListSorted =  customQuery.getResultList();
            
            return allCategoryListSorted;
        }
        finally
        {
            entityManager.close();
        }
    }

    public Category get(int inputCategoryID) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        
        try
        {
            Category tempCategory = entityManager.find(Category.class, inputCategoryID);
            return tempCategory;
        }
        finally
        {
            entityManager.close();
        }
    }

    public void insert(Category inputCategory) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            entityManager.persist(inputCategory);
            
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

    public void update(Category inputCategory) throws Exception 
    {
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            entityManager.merge(inputCategory);
            
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

    public void delete(Category inputCategory) throws Exception 
    {        
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            entityManager.remove(inputCategory);
            
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
    
    public int getNewCategoryID() throws Exception
    {
        int newID = -1;
        
        EntityManager entityManager = DBUtil.getEntityFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try
        {
            entityTransaction.begin();
            
            Query customQuery = entityManager.createQuery("SELECT MAX(c.categoryID) FROM Category c");
            
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
