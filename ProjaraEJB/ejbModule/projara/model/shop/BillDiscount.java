/***********************************************************************
 * Module:  BillDiscount.java
 * Author:  Stanko
 * Purpose: Defines the Class BillDiscount
 ***********************************************************************/

package projara.model.shop;

import java.io.Serializable;



/** @pdOid c05e91d1-9a51-46b5-9ffd-f9cf2aa5e690 */
public class BillDiscount implements Serializable{
   /** @pdOid ebebe7a4-3272-4d93-9d28-0959476fdf4e */
   private int id;
   /** @pdOid f173535d-effc-429f-9787-46929bf49bc5 */
   private double discount;
   /** @pdOid 33b78669-409e-48d7-9b56-a57e1cc68340 */
   private String type;
   
   /** @pdRoleInfo migr=no name=Bill assc=hasDiscount mult=1..1 side=A */
   private Bill bill;
   
   
   /** @pdGenerated default parent getter */
   public Bill getBill() {
      return bill;
   }
   
   /** @pdGenerated default parent setter
     * @param newBill */
   public void setBill(Bill newBill) {
      if (this.bill == null || !this.bill.equals(newBill))
      {
         if (this.bill != null)
         {
            Bill oldBill = this.bill;
            this.bill = null;
            oldBill.removeBillDiscounts(this);
         }
         if (newBill != null)
         {
            this.bill = newBill;
            this.bill.addBillDiscounts(this);
         }
      }
   }
   
   /** @pdOid 071248c5-97d4-4116-bc74-2d401f6c6539 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 7147705d-1bcd-44c6-a2ef-ff3284d52696 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid e875966b-f8df-4fb6-aaf1-b185b4841fa4 */
   public double getDiscount() {
      return discount;
   }
   
   /** @param newDiscount
    * @pdOid 812ef6a2-2513-4aec-bffc-235a824fe217 */
   public void setDiscount(double newDiscount) {
      discount = newDiscount;
   }
   
   /** @pdOid 18f53b1d-8d10-4daa-883a-9f7b8f5e0e3d */
   public String getType() {
      return type;
   }
   
   /** @param newType
    * @pdOid 4160db05-bd77-4102-b6e4-87de933d39c6 */
   public void setType(String newType) {
      type = newType;
   }

}