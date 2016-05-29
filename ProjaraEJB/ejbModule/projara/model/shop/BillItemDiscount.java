/***********************************************************************
 * Module:  BillItemDiscount.java
 * Author:  Stanko
 * Purpose: Defines the Class BillItemDiscount
 ***********************************************************************/

package projara.model.shop;

import java.io.Serializable;


/** @pdOid c2f9f85e-0f6b-4f23-9a54-a1d250c2462a */
public class BillItemDiscount implements Serializable{
   /** @pdOid bebd456d-f9d9-477b-8913-70fff361b424 */
   private int id;
   /** @pdOid 51f7ab44-fa01-48d6-9751-bd1a354a77ec */
   private double discount;
   /** @pdOid 1613481f-9e5e-4931-b2cd-eb1fbf07c2ea */
   private String type;
   
   /** @pdRoleInfo migr=no name=BillItem assc=itemHasDiscounts mult=1..1 side=A */
   private BillItem billItem;
   
   
   /** @pdGenerated default parent getter */
   public BillItem getBillItem() {
      return billItem;
   }
   
   /** @pdGenerated default parent setter
     * @param newBillItem */
   public void setBillItem(BillItem newBillItem) {
      if (this.billItem == null || !this.billItem.equals(newBillItem))
      {
         if (this.billItem != null)
         {
            BillItem oldBillItem = this.billItem;
            this.billItem = null;
            oldBillItem.removeDiscounts(this);
         }
         if (newBillItem != null)
         {
            this.billItem = newBillItem;
            this.billItem.addDiscounts(this);
         }
      }
   }
   
   /** @pdOid f5575ec8-63a6-4609-b628-a81aa488310b */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid c4534eb4-c79f-4329-99cc-71e1c1908779 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid ee54875a-45ae-45eb-88a1-d89b7471f593 */
   public double getDiscount() {
      return discount;
   }
   
   /** @param newDiscount
    * @pdOid 835fb1ac-7e5b-4196-b95c-33ae37921858 */
   public void setDiscount(double newDiscount) {
      discount = newDiscount;
   }
   
   /** @pdOid ac813135-57b5-4227-9f10-d8b146f06609 */
   public String getType() {
      return type;
   }
   
   /** @param newType
    * @pdOid ec3d5cbd-3020-46a0-94a6-a97dec19458d */
   public void setType(String newType) {
      type = newType;
   }

}