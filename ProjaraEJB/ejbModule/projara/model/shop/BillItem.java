/***********************************************************************
 * Module:  BillItem.java
 * Author:  Stanko
 * Purpose: Defines the Class BillItem
 ***********************************************************************/

package projara.model.shop;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import projara.model.items.Item;


/** @pdOid e60b0e1d-4b7c-4a41-a770-13911a160730 */
public class BillItem implements Serializable{
   /** @pdOid 0a6a2e9a-0bd5-4ce8-95c6-620c671e36e7 */
   private double no;
   /** @pdOid 071bff18-0e35-47cb-9930-9ec017b1d89b */
   private double price;
   /** @pdOid cc71a2ca-bc74-479c-8380-cebde16c943a */
   private double quantity;
   /** @pdOid 95528321-63b3-449e-a520-f8382da72a7e */
   private double originalTotal;
   /** @pdOid 95bc9e4f-033e-436b-a7cd-01a64037b102 */
   private double discountPercentage;
   /** @pdOid 401fcef4-b8f6-432e-a5d0-01f6692fdad0 */
   private double total;
   
   /** @pdRoleInfo migr=no name=BillItemDiscount assc=itemHasDiscounts coll=Set impl=HashSet mult=0..* */
   private Set<BillItemDiscount> discounts;
   /** @pdRoleInfo migr=no name=Item assc=isOnBill mult=1..1 side=A */
   private Item item;
   /** @pdRoleInfo migr=no name=Bill assc=hasItems mult=1..1 side=A */
   private Bill bill;
   
   
   /** @pdGenerated default getter */
   public Set<BillItemDiscount> getDiscounts() {
      if (discounts == null)
         discounts = new HashSet<BillItemDiscount>();
      return discounts;
   }
   
   /** @pdGenerated default iterator getter */
   public Iterator getIteratorDiscounts() {
      if (discounts == null)
         discounts = new HashSet<BillItemDiscount>();
      return discounts.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newDiscounts */
   public void setDiscounts(Set<BillItemDiscount> newDiscounts) {
      removeAllDiscounts();
      for (Iterator iter = newDiscounts.iterator(); iter.hasNext();)
         addDiscounts((BillItemDiscount)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newBillItemDiscount */
   public void addDiscounts(BillItemDiscount newBillItemDiscount) {
      if (newBillItemDiscount == null)
         return;
      if (this.discounts == null)
         this.discounts = new HashSet<BillItemDiscount>();
      if (!this.discounts.contains(newBillItemDiscount))
      {
         this.discounts.add(newBillItemDiscount);
         newBillItemDiscount.setBillItem(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldBillItemDiscount */
   public void removeDiscounts(BillItemDiscount oldBillItemDiscount) {
      if (oldBillItemDiscount == null)
         return;
      if (this.discounts != null)
         if (this.discounts.contains(oldBillItemDiscount))
         {
            this.discounts.remove(oldBillItemDiscount);
            oldBillItemDiscount.setBillItem((BillItem)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllDiscounts() {
      if (discounts != null)
      {
         BillItemDiscount oldBillItemDiscount;
         for (Iterator iter = getIteratorDiscounts(); iter.hasNext();)
         {
            oldBillItemDiscount = (BillItemDiscount)iter.next();
            iter.remove();
            oldBillItemDiscount.setBillItem((BillItem)null);
         }
      }
   }
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
            oldBill.removeItems(this);
         }
         if (newBill != null)
         {
            this.bill = newBill;
            this.bill.addItems(this);
         }
      }
   }
   
   /** @pdOid ce4aba9c-ef63-4df0-8ff3-5062436d47d4 */
   public double getNo() {
      return no;
   }
   
   /** @param newNo
    * @pdOid 0f9d8012-a4d3-4247-979d-65e6a1a9e584 */
   public void setNo(double newNo) {
      no = newNo;
   }
   
   /** @pdOid c477692e-a12f-40e0-b5ec-3b6ee9fac8bd */
   public double getPrice() {
      return price;
   }
   
   /** @param newPrice
    * @pdOid f4f4d936-f3bd-451b-998d-5a715b0bf814 */
   public void setPrice(double newPrice) {
      price = newPrice;
   }
   
   /** @pdOid 3eab79b6-da08-4c92-9e8e-9fcac035abac */
   public double getQuantity() {
      return quantity;
   }
   
   /** @param newQuantity
    * @pdOid f8e397ec-ed3f-47e0-aa54-bc8cbddd2b2f */
   public void setQuantity(double newQuantity) {
      quantity = newQuantity;
   }
   
   /** @pdOid bb4f3017-dd52-43ed-b03e-4dae54480a30 */
   public double getOriginalTotal() {
      return originalTotal;
   }
   
   /** @param newOriginalTotal
    * @pdOid 7926e861-535d-49c0-8175-38dd403d9a03 */
   public void setOriginalTotal(double newOriginalTotal) {
      originalTotal = newOriginalTotal;
   }
   
   /** @pdOid 2c6b4a73-dda1-4aa1-a9df-c904ffceb1fc */
   public double getDiscountPercentage() {
      return discountPercentage;
   }
   
   /** @param newDiscountPercentage
    * @pdOid d19a86df-540f-429b-897f-e13d78f78fe0 */
   public void setDiscountPercentage(double newDiscountPercentage) {
      discountPercentage = newDiscountPercentage;
   }
   
   /** @pdOid 155612f0-6181-40c9-a516-b026ba4979c6 */
   public double getTotal() {
      return total;
   }
   
   /** @param newTotal
    * @pdOid 8c6d86f2-5c0d-481a-bc21-d532e862c696 */
   public void setTotal(double newTotal) {
      total = newTotal;
   }

}