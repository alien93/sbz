package projara.model.users;

/***********************************************************************
 * Module:  User.java
 * Author:  Stanko
 * Purpose: Defines the Class User
 ***********************************************************************/


import java.io.Serializable;

/** @pdOid 8329c82e-1993-49b7-8e06-5aba74b4499d */
public class User implements Serializable{
   /** @pdOid b832cb24-04de-4ac9-851f-0a219d727bcd */
   protected int id;
   /** @pdOid 596826ce-d9de-4ebf-9467-f293833d9072 */
   protected String username;
   /** @pdOid a890a89c-857b-4c1f-b838-000bf785f39d */
   protected String firstName;
   /** @pdOid 4c359fed-49cf-4824-82f9-98e74ff71f4f */
   protected String lastName;
   /** @pdOid 5a98c718-4012-4ccb-956a-29a2ef2cd030 */
   protected java.util.Date registeredOn;
   /** @pdOid cc04357d-6232-46ac-bddc-0f7180a73a97 */
   protected String role;
   
   /** @pdOid 37fc1502-b114-4341-b0b9-13a6fc0a5362 */
   protected void finalize() {
      // TODO: implement
   }
   
   /** @pdOid ea49e095-aeac-46d0-9f73-cf7f3d0ff5f0 */
   public int getId() {
      return id;
   }
   
   /** @param newId
    * @pdOid abcdac88-009a-46c0-b3e0-59086402ac40 */
   public void setId(int newId) {
      id = newId;
   }
   
   /** @pdOid b20d7bb0-2e6a-4e45-ab79-b58935a74a9f */
   public String getUsername() {
      return username;
   }
   
   /** @param newUsername
    * @pdOid 92ea60da-50be-411c-963c-be205a9cf168 */
   public void setUsername(String newUsername) {
      username = newUsername;
   }
   
   /** @pdOid 06804a1c-c022-4d87-a472-92a81175767e */
   public String getFirstName() {
      return firstName;
   }
   
   /** @param newFirstName
    * @pdOid 77ee2c46-6d0c-44ea-94ab-9103aafe4b27 */
   public void setFirstName(String newFirstName) {
      firstName = newFirstName;
   }
   
   /** @pdOid 1966c38e-e146-4b67-b73b-390704baa785 */
   public String getLastName() {
      return lastName;
   }
   
   /** @param newLastName
    * @pdOid 2b1cb035-1665-435d-aa28-651eba8f901b */
   public void setLastName(String newLastName) {
      lastName = newLastName;
   }
   
   /** @pdOid e0d731fa-3843-4ce6-a8dc-7e6d56ed4af4 */
   public java.util.Date getRegisteredOn() {
      return registeredOn;
   }
   
   /** @param newRegisteredOn
    * @pdOid 164265bd-3adf-4b7f-bb4e-fe434a428bf2 */
   public void setRegisteredOn(java.util.Date newRegisteredOn) {
      registeredOn = newRegisteredOn;
   }
   
   /** @pdOid c4a962a1-fb9e-4284-b548-ad2fbc412b30 */
   public String getRole() {
      return role;
   }
   
   /** @param newRole
    * @pdOid 5a07f7ad-38de-4813-b88f-76dc78625f7f */
   public void setRole(String newRole) {
      role = newRole;
   }
   
   /** @pdOid a53c3467-48e9-4674-9e85-ce1819a4508d */
   public User() {
      // TODO: implement
   }

}
