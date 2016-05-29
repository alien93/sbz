/***********************************************************************
 * Module:  CustomerCategory.java
 * Author:  Stanko
 * Purpose: Defines the Class CustomerCategory
 ***********************************************************************/

package projara.model.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/** @pdOid 4b5b8702-abc4-499e-9efc-3e8f2a73fb4a */
public class CustomerCategory implements Serializable{
   /** @pdOid e32533f7-f782-4a82-9ec6-719e9c0dda5b */
   private String categoryCode;
   /** @pdOid 8cc2e116-dc11-4669-86bd-546326f8b69f */
   private String name;
   
   /** @pdRoleInfo migr=no name=Threshold assc=hasThresholds coll=Set impl=HashSet mult=0..* side=A */
   public Set<Threshold> thresholds;
   
   /** @pdOid c5b2aa2f-e5ae-4b5c-a831-acfbe4180200 */
   public String getCategoryCode() {
      return categoryCode;
   }
   
   /** @param newCategoryCode
    * @pdOid 95a16977-6e15-46ee-ac48-d79595ca2ae7 */
   public void setCategoryCode(String newCategoryCode) {
      categoryCode = newCategoryCode;
   }
   
   /** @pdOid feba07e3-7c11-479b-9263-5235ea6e304d */
   public String getName() {
      return name;
   }
   
   /** @param newName
    * @pdOid 7d0ff594-7e54-489c-b380-a362bce8b891 */
   public void setName(String newName) {
      name = newName;
   }
   
   
   /** @pdGenerated default getter */
   public Set<Threshold> getThresholds() {
      if (thresholds == null)
         thresholds = new HashSet<Threshold>();
      return thresholds;
   }
   
   /** @pdGenerated default iterator getter */
   public Iterator getIteratorThresholds() {
      if (thresholds == null)
         thresholds = new HashSet<Threshold>();
      return thresholds.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newThresholds */
   public void setThresholds(Set<Threshold> newThresholds) {
      removeAllThresholds();
      for (Iterator iter = newThresholds.iterator(); iter.hasNext();)
         addThresholds((Threshold)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newThreshold */
   public void addThresholds(Threshold newThreshold) {
      if (newThreshold == null)
         return;
      if (this.thresholds == null)
         this.thresholds = new HashSet<Threshold>();
      if (!this.thresholds.contains(newThreshold))
      {
         this.thresholds.add(newThreshold);
         newThreshold.addCategories(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldThreshold */
   public void removeThresholds(Threshold oldThreshold) {
      if (oldThreshold == null)
         return;
      if (this.thresholds != null)
         if (this.thresholds.contains(oldThreshold))
         {
            this.thresholds.remove(oldThreshold);
            oldThreshold.removeCategories(this);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllThresholds() {
      if (thresholds != null)
      {
         Threshold oldThreshold;
         for (Iterator iter = getIteratorThresholds(); iter.hasNext();)
         {
            oldThreshold = (Threshold)iter.next();
            iter.remove();
            oldThreshold.removeCategories(this);
         }
      }
   }

}