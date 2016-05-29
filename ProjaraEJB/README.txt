Konektor (jar) za mySql bazu stavite u modules/com/mysql/main.
JAR fajl se nalazi u ProjaraEJB/lib
U tom folderu napravite module.xml i u njega unesite:
*************************************************************
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.0" name="com.mysql" slot="main">
  <resources>
     <resource-root path="mysql-connector-java-5.1.21-bin.jar"/>              
  </resources>
  <dependencies>
     <module name="javax.api"/>
  </dependencies>
</module>
**************************************************************
U standalone-ful.xml (ili koji vec koristite za server) u delu <drivers></drivers> 
unesi:
************************************************************
<driver name="mysql" module="com.mysql"/>
***********************************************************
A u delu <datasources></datasources> unesi podelement:
***********************************************************
<datasource
        jndi-name="java:/sbz" pool-name="my_pool"
        enabled="true" jta="true"
        use-java-context="true" use-ccm="true">
        <connection-url>
             jdbc:mysql://localhost:3306/webshop_sbz
        </connection-url>
        <driver>
             mysql
        </driver>
        <security>
             <user-name>
                  USERNAME
             </user-name>
             <password>
                  PASSWORD
             </password>
        </security>
        <statement>
             <prepared-statement-cache-size>
                  100
             </prepared-statement-cache-size>
             <share-prepared-statements/>
        </statement>
 </datasource>
 ******************************************************************
 Zamenite USERNAME i PASSWORD.
 Potrebno je da kreirate u mySQL semu pod nazivom 'webshop_sbz'.
 Ako ste bili dobri prosle godine i ako imate srece ovo mozda proradi :)
 Ako ne dobijete exception, ne znaci da ovo radi :)