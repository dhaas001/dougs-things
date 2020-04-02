package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@NamedQueries({
    @NamedQuery(name="InventoryCategory.findAll",
                query="SELECT c FROM InventoryCategory c"),
    @NamedQuery(name="InventoryCategory.findByName",
                query="SELECT c FROM InventoryCategory c WHERE c.categoryName = :name"),
}) 

@Table(name="Inventory_Category")
public class InventoryCategory
{
	public static final String INVENTORY_CATEGORY_FIND_ALL = "InventoryCategory.findAll";
	public static final String INVENTORY_CATEGORY_FIND_BY_NAME = "InventoryCategory.findByName";
   private int version;
   private int id;

   private String categoryName;
   private String categoryDescription;


   public InventoryCategory(){}

   @Column(name = "categoryName")
   public String getCategoryName()
   {
      return categoryName;
   }

   public void setCategoryName(String name)
   {
      this.categoryName = name;
   }

   @Column(name = "itemDescription")
   public String getCategoryDescription()
   {
      return categoryDescription;
   }

   public void setCategoryDescription(String description)
   {
      this.categoryDescription = description;
   }

   @Version
   @Column(name = "version_field")
   // not required
   public int getVersion()
   {
       return version;
   }

   public void setVersion(int version)
   {
      this.version = version;
   }

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   public int getId()
   {
      return id;
   }

   public void setId(int id)
   {
      this.id = id;
   }
}
