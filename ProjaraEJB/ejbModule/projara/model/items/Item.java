/***********************************************************************
 * Module:  Item.java
 * Author:  Stanko
 * Purpose: Defines the Class Item
 ***********************************************************************/

package projara.model.items;

import java.io.Serializable;
import java.util.Date;


/** @pdOid e57bd824-c576-430b-bece-d500604bfcf0 */
public class Item implements Serializable{
   /** @pdOid 8dad3df8-c3ec-4c71-8a6e-244868d2e25e */
   private int id;
   /** @pdOid 21cd9c0f-ee51-4113-a854-5acb59900162 */
   private String name;
   /** @pdOid 586e348c-6b8f-42a7-bb1b-d822ec5870ad */
   private double price;
   /** @pdOid fbf7c284-e217-4538-bdad-aeb919efdb9a */
   private int inStock;
   /** @pdOid c5127174-1ca2-4f4a-a080-5f832fdb2d69 */
   private Date createdOn;
   /** @pdOid e0e6bdb8-4e31-472d-bf62-3f8f1c92ff2d */
   private boolean needOrdering;
   /** @pdOid 26718804-e665-474c-b35e-dcdf37b6b242 */
   private boolean recordState;
   /** @pdOid 8a74dbe9-dbc9-423d-893f-90d503466737 */
   private int minQuantity = 0;
   
   /** @pdRoleInfo migr=no name=ItemCategory assc=itemCategory mult=1..1 side=A */
   public ItemCategory category;
   
   /** @pdOid 586f979d-033f-4952-961e-9e62f92469a1 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 328dc462-4ee1-4a99-8c03-e4fcce46754e */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid fe3e07d1-2816-465d-bcec-7c75fd407813 */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid 600a9ec7-1f6c-4799-a691-1fad6f1089e2 */
   public void setName(String newName) {
      name = newName;
   }
   
   /** @pdOid 59e67162-ce3a-4821-9905-cc509f8b6e9c */
   public double getPrice() {
      return price;
   }
   
   /** @param newPrice
    * @pdOid 397fc980-a0d9-48d9-be8f-3cefcd99217e */
   public void setPrice(double newPrice) {
      price = newPrice;
   }
   
   /** @pdOid 0f6c9f69-3e83-4912-9245-72896fa82053 */
   public int getInStock() {
      return inStock;
   }
   
   /** @param newInStock
    * @pdOid 00bd2d58-5579-41c5-b921-032b1c637aea */
   public void setInStock(int newInStock) {
      inStock = newInStock;
   }
   
   /** @pdOid 6250c152-0234-44e4-891a-27512447996d */
   public Date getCreatedOn() {
      return createdOn;
   }
   
   /** @param newCreatedOn
    * @pdOid 633188ad-c6c9-4df3-8bcb-d17594a18bb8 */
   public void setCreatedOn(Date newCreatedOn) {
      createdOn = newCreatedOn;
   }
   
   /** @pdOid b5c380f7-6193-402e-bff4-6647cef49335 */
   public boolean getNeedOrdering() {
      return needOrdering;
   }
   
   /** @param newNeedOrdering
    * @pdOid 8c2bed4b-35fc-4f8f-b78c-05eb13b327c8 */
   public void setNeedOrdering(boolean newNeedOrdering) {
      needOrdering = newNeedOrdering;
   }
   
   /** @pdOid b7b097df-1ae2-446b-8927-106b418d9411 */
   public boolean getRecordState() {
      return recordState;
   }
   
   /** @param newRecordState
    * @pdOid b2bfd678-e16b-4e1d-a402-c90cf9840864 */
   public void setRecordState(boolean newRecordState) {
      recordState = newRecordState;
   }
   
   /** @pdOid e61162d5-79e8-460e-b5c8-3d08c50de285 */
   public int getMinQuantity() {
      return minQuantity;
   }
   
   /** @param newMinQuantity
    * @pdOid 2603a15b-63ec-4d95-850b-b32ba2b705d2 */
   public void setMinQuantity(int newMinQuantity) {
      minQuantity = newMinQuantity;
   }
   
   
   /** @pdGenerated default parent getter */
   public ItemCategory getCategory() {
      return category;
   }
   
   /** @pdGenerated default parent setter
     * @param newItemCategory */
   public void setCategory(ItemCategory newItemCategory) {
      if (this.category == null || !this.category.equals(newItemCategory))
      {
         if (this.category != null)
         {
            ItemCategory oldItemCategory = this.category;
            this.category = null;
            oldItemCategory.removeItems(this);
         }
         if (newItemCategory != null)
         {
            this.category = newItemCategory;
            this.category.addItems(this);
         }
      }
   }

}