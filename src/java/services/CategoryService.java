/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import data_access_layer.CategoryDB;
import java.util.List;
import models.Category;

/**
 *
 * @author BritishWaldo
 */
public class CategoryService
{
    public Category get(int inputCategoryID) throws Exception 
    {
        CategoryDB categoryConnection = new CategoryDB();
        Category tempCategory = categoryConnection.get(inputCategoryID);
        return tempCategory;
    }
    
    public List<Category> getAll() throws Exception 
    {
        CategoryDB categoryConnection = new CategoryDB();
        List<Category> allCategoryList = categoryConnection.getAll();
        return allCategoryList;
    }
    
    public List<Category> getAllSorted(String columnName) throws Exception
    {
        CategoryDB categoryConnection = new CategoryDB();
        List<Category> allCategoryList = categoryConnection.getAllSorted(columnName);
        return allCategoryList;
    }
    
    public void insert(String newCategoryName) throws Exception 
    {
        CategoryDB categoryConnection = new CategoryDB();
        
        //query database to get the next valid id
        int newCategoryID = categoryConnection.getNewCategoryID();
        
        Category tempCategory = new Category(newCategoryID, newCategoryName);
        categoryConnection.insert(tempCategory);
    }
    
    public void update(Integer newCategoryID, String newCategoryName) throws Exception 
    {
        CategoryDB categoryConnection = new CategoryDB();
        Category tempCategory = this.get(newCategoryID);
        
        if (!tempCategory.getCategoryName().equals(newCategoryName))
        {
            tempCategory.setCategoryName(newCategoryName);
        }
        
        categoryConnection.update(tempCategory);
    }
    
    public void delete(int inputCategoryID) throws Exception 
    {
        CategoryDB categoryConnection = new CategoryDB();
        Category tempCategory = new Category(inputCategoryID);
        categoryConnection.update(tempCategory);
    }      
}
