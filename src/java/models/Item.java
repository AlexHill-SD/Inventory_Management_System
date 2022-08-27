/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BritishWaldo
 */
@Entity
@Table(name = "items", catalog = "homeinventorydb", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")
    , @NamedQuery(name = "Item.findByItemID", query = "SELECT i FROM Item i WHERE i.itemID = :itemID")
    , @NamedQuery(name = "Item.findByItemName", query = "SELECT i FROM Item i WHERE i.itemName = :itemName")
    , @NamedQuery(name = "Item.findByPrice", query = "SELECT i FROM Item i WHERE i.price = :price")
})
public class Item implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ItemID")
    private Integer itemID;
    @Basic(optional = false)
    @Column(name = "ItemName")
    private String itemName;
    @Basic(optional = false)
    @Column(name = "Price")
    private double price;
    @JoinColumn(name = "Category", referencedColumnName = "CategoryID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Category category;
    @JoinColumn(name = "Owner", referencedColumnName = "Username")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User owner;

    public Item()
    {
    }

    public Item(Integer itemID)
    {
        this.itemID = itemID;
    }

    public Item(int newItemID, String newItemName, double newPrice, Category newItemCategory, User newItemOwner)
    {
        this.itemID = newItemID;
        this.itemName = newItemName;
        this.price = newPrice;
        this.category = newItemCategory;
        this.owner = newItemOwner;
    }

    public Integer getItemID()
    {
        return itemID;
    }

    public void setItemID(Integer itemID)
    {
        this.itemID = itemID;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (itemID != null ? itemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item))
        {
            return false;
        }
        Item other = (Item) object;
        if ((this.itemID == null && other.itemID != null) || (this.itemID != null && !this.itemID.equals(other.itemID)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "models.Item[ itemID=" + itemID + " ]";
    }
    
}
