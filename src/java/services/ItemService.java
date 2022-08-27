/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import data_access_layer.ItemDB;
import java.util.List;
import models.Category;
import models.Item;
import models.User;

/**
 *
 * @author BritishWaldo
 */
public class ItemService
{
    public Item get(int inputItemID) throws Exception 
    {
        ItemDB itemConnection = new ItemDB();
        Item tempItem = itemConnection.get(inputItemID);
        return tempItem;
    }
    
    public List<Item> getAll() throws Exception 
    {
        ItemDB itemConnection = new ItemDB();
        List<Item> allItemList = itemConnection.getAll();
        return allItemList;
    }
    
    public List<Item> getAllSortedForUser(String username, String columnName) throws Exception
    {
        ItemDB itemConnection = new ItemDB();
        List<Item> allItemList = itemConnection.getAllSortedForUser(username, columnName);
        return allItemList;
    }
    
    public void insert(String newItemName, double newPrice, int newItemCategory, String newOwnerUsername) throws Exception 
    {
        ItemDB itemConnection = new ItemDB();
        
        User tempOwner = new UserService().get(newOwnerUsername);
        Category tempCategory = new CategoryService().get(newItemCategory);
        int tempItemID = itemConnection.getNewItemID();
        
        Item tempItem = new Item(tempItemID, newItemName, newPrice, tempCategory, tempOwner);
        itemConnection.insert(tempItem);
    }
    
    public void update(Integer inputItemID, String inputItemName, double inputPrice, int newItemCategory) throws Exception 
    {
        ItemDB itemConnection = new ItemDB();
        Item tempItem = this.get(inputItemID);
        
        if (!tempItem.getItemName().equals(inputItemName))
        {
            tempItem.setItemName(inputItemName);
        }
        
        if (tempItem.getPrice() != inputPrice)
        {
            tempItem.setPrice(inputPrice);
        }
        
        if (tempItem.getCategory().getCategoryID() != newItemCategory)
        {
            Category tempCategory = new CategoryService().get(newItemCategory);
            tempItem.setCategory(tempCategory);
        }
        
        itemConnection.update(tempItem);
    }
    
    public void delete(int inputItemID) throws Exception 
    {
        ItemDB itemConnection = new ItemDB();
        Item tempItem = new Item(inputItemID);
        itemConnection.delete(tempItem);
    }    
}