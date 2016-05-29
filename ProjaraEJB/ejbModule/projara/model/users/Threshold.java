/***********************************************************************
 * Module:  Threshold.java
 * Author:  Stanko
 * Purpose: Defines the Class Threshold
 ***********************************************************************/

package projara.model.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/** @pdOid 8f2253a3-68b0-4803-bf5b-3603cc63a8b1 */
public class Threshold implements Serializable{
   /** @pdOid 6d16fe6b-edd8-4a2c-bed9-0adbae567bad */
   private double id;
   /** @pdOid b10c9d29-f8f8-4837-93b5-ad4868c72940 */
   private double from;
   /** @pdOid dc6b76aa-715e-4251-9d3a-5a793d670a99 */
   private double to;
   /** @pdOid 1c7e3372-310f-410f-83bb-3686d757bd8a */
   private double percentage;
   
   /** @pdRoleInfo migr=no name=CustomerCategory assc=hasThresholds coll=Set impl=HashSet mult=0..* */
   public Set<CustomerCategory> categories;
   
   /** @pdOid e36b431e-0223-49a3-b4d1-f84abeefeef7 */
   public double getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid 11291e0d-b2eb-41d6-93d7-966736e3df98 */
   public void setId(double newId) {
      id = newId;
   }
   
   /** @pdOid 577735c6-9777-4843-86d1-1b44718f9199 */
   public double getFrom() {
      return from;
   }
   
   /** @param newFrom
    * @pdOid 4380a561-5e93-4e73-80d8-1a7c08fea76f */
   public void setFrom(double newFrom) {
      from = newFrom;
   }
   
   /** @pdOid 189a05ac-dbc9-4f93-910b-d5449c80af92 */
   public double getTo() {
      return to;
   }
   
   /** @param newTo
    * @pdOid 3cf0520b-4280-406d-b163-e17a3f54d190 */
   public void setTo(double newTo) {
      to = newTo;
   }
   
   /** @pdOid 39458b53-3bbb-4731-8d9f-8b1e4a4c619a */
   public double getPercentage() {
      return percentage;
   }
   
   /** @param newPercentage
    * @pdOid c19feede-6166-4f7c-b28a-9ce0ac6dc9b6 */
   public void setPercentage(double newPercentage) {
      percentage = newPercentage;
   }
   
   
   /** @pdGenerated default getter */
   public Set<CustomerCategory> getCategories() {
      if (categories == null)
         categories = new HashSet<CustomerCategory>();
      return categories;
   }
   
   /** @pdGenerated default iterator getter */
   public Iterator getIteratorCategories() {
      if (categories == null)
         categories = new HashSet<CustomerCategory>();
      return categories.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newCategories */
   public void setCategories(Set<CustomerCategory> newCategories) {
      removeAllCategories();
      for (Iterator iter = newCategories.iterator(); iter.hasNext();)
         addCategories((CustomerCategory)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newCustomerCategory */
   public void addCategories(CustomerCategory newCustomerCategory) {
      if (newCustomerCategory == null)
         return;
      if (this.categories == null)
         this.categories = new HashSet<CustomerCategory>();
      if (!this.categories.contains(newCustomerCategory))
      {
         this.categories.add(newCustomerCategory);
         newCustomerCategory.addThresholds(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldCustomerCategory */
   public void removeCategories(CustomerCategory oldCustomerCategory) {
      if (oldCustomerCategory == null)
         return;
      if (this.categories != null)
         if (this.categories.contains(oldCustomerCategory))
         {
            this.categories.remove(oldCustomerCategory);
            oldCustomerCategory.removeThresholds(this);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllCategories() {
      if (categories != null)
      {
         CustomerCategory oldCustomerCategory;
         for (Iterator iter = getIteratorCategories(); iter.hasNext();)
         {
            oldCustomerCategory = (CustomerCategory)iter.next();
            iter.remove();
            oldCustomerCategory.removeThresholds(this);
         }
      }
   }

}