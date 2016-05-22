/*CUSTOMER CATEGORY*/
INSERT INTO `sbz_shema`.`customer_category` (`CAT_ID`, `CAT_NAME`) VALUES ('A', 'Category A');
INSERT INTO `sbz_shema`.`customer_category` (`CAT_ID`, `CAT_NAME`) VALUES ('B', 'Category B');
INSERT INTO `sbz_shema`.`customer_category` (`CAT_ID`, `CAT_NAME`) VALUES ('C', 'Category C');

/*THRESHOLD*/
INSERT INTO `sbz_shema`.`threshold` (`THRES_ID`, `THRES_FROM`, `THRES_TO`, `THRES_PERCENT`) VALUES ('1', '1000', '2000', '5');
INSERT INTO `sbz_shema`.`threshold` (`THRES_ID`, `THRES_FROM`, `THRES_TO`, `THRES_PERCENT`) VALUES ('2', '3000', '4000', '10');
INSERT INTO `sbz_shema`.`threshold` (`THRES_ID`, `THRES_FROM`, `THRES_TO`, `THRES_PERCENT`) VALUES ('3', '5000', '6000', '15');

/*HAS-TRASHOLDS*/
INSERT INTO `sbz_shema`.`has_thresholds` (`THRES_ID`, `CAT_ID`) VALUES ('1', 'A');
INSERT INTO `sbz_shema`.`has_thresholds` (`THRES_ID`, `CAT_ID`) VALUES ('2', 'B');
INSERT INTO `sbz_shema`.`has_thresholds` (`THRES_ID`, `CAT_ID`) VALUES ('3', 'C');
INSERT INTO `sbz_shema`.`has_thresholds` (`THRES_ID`, `CAT_ID`) VALUES ('1', 'B');

/*USER*/
INSERT INTO `sbz_shema`.`user` (`USR_ID`, `USR_USERNAME`, `USR_FNAME`, `USR_LNAME`, `USR_REGDAT`, `USR_ROLE`) VALUES ('1', 'pera', 'Pera', 'Peric', CURRENT_TIMESTAMP(), 'M');
INSERT INTO `sbz_shema`.`user` (`USR_ID`, `CAT_ID`, `USR_USERNAME`, `USR_FNAME`, `USR_LNAME`, `USR_REGDAT`, `USR_ROLE`, `USR_CUS_ADDRESS`, `USR_CUS_POINT`, `USR_CUS_RESERVED`) VALUES ('2', 'A', 'djura', 'Djura', 'Djuric', CURRENT_TIMESTAMP(), 'C', 'Adresa', '10', '0');
INSERT INTO `sbz_shema`.`user` (`USR_ID`, `CAT_ID`, `USR_USERNAME`, `USR_FNAME`, `USR_LNAME`, `USR_REGDAT`, `USR_ROLE`, `USR_CUS_ADDRESS`, `USR_CUS_POINT`, `USR_CUS_RESERVED`) VALUES ('3', 'B', 'mika', 'Mika', 'Mikic', CURRENT_TIMESTAMP(), 'C', 'Adresa 2', '500', '20');
INSERT INTO `sbz_shema`.`user` (`USR_ID`, `USR_USERNAME`, `USR_FNAME`, `USR_LNAME`, `USR_REGDAT`, `USR_ROLE`) VALUES ('4',  'petar', 'Petar', 'Petrovic', CURRENT_TIMESTAMP(), 'V');

/*ITEM_CATEGORY*/
INSERT INTO `sbz_shema`.`item_category` (`ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('AAA', 'PC operma', '10');
INSERT INTO `sbz_shema`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('AAB', 'AAA', 'Kućišta', '4');
INSERT INTO `sbz_shema`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('AAC', 'AAB', 'Drveno :)', '20');
INSERT INTO `sbz_shema`.`item_category` (`ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('BBB', 'Knjiga', '10');

/*ITEM*/
INSERT INTO `sbz_shema`.`item` (`IT_ID`, `ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`) VALUES ('1', 'AAA', 'DVD RW', '2500', '20', CURRENT_TIMESTAMP(), '0', '1', '10');
INSERT INTO `sbz_shema`.`item` (`IT_ID`, `ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`) VALUES ('2', 'AAB', 'Neko super kuciste', '5000', '10', CURRENT_TIMESTAMP(), '0', '0', '15');
INSERT INTO `sbz_shema`.`item` (`IT_ID`, `ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`) VALUES ('3', 'AAC', 'Nesto bez veze', '500', '40', CURRENT_TIMESTAMP(), '0', '0', '5');
INSERT INTO `sbz_shema`.`item` (`IT_ID`, `ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`) VALUES ('4', 'AAA', 'Monitor LCD', '6000', '10', CURRENT_TIMESTAMP(), '1', '1', '20');
INSERT INTO `sbz_shema`.`item` (`IT_ID`, `ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`) VALUES ('5', 'BBB', 'The name of the Wind', '1200', '20', CURRENT_TIMESTAMP(), '0', '1', '10');

/*ACTION EVENTS*/
INSERT INTO `sbz_shema`.`action_event` (`AE_ID`, `AE_NAME`, `AE_FROM`, `AE_UNTIL`, `AE_DISCOUNT`) VALUES ('1', 'Novogodisnji popust', '2015-12-15 23:59:59', '2016-01-10 23:59:59', '10');
INSERT INTO `sbz_shema`.`action_event` (`AE_ID`, `AE_NAME`, `AE_FROM`, `AE_UNTIL`, `AE_DISCOUNT`) VALUES ('2', 'Prvomajski popust', '2015-04-25 23:59:59', '2015-05-04 23:59:59', '5');

/*ON DISCOUNT*/
INSERT INTO `sbz_shema`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('1', 'AAB');
INSERT INTO `sbz_shema`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('2', 'AAA');
INSERT INTO `sbz_shema`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('1', 'BBB');

/*BILL*/
INSERT INTO `sbz_shema`.`bill` (`BILL_ID`, `USR_ID`, `BILL_DATE`, `BILL_STATE`) VALUES ('1', '2', CURRENT_TIMESTAMP(), 'O');
INSERT INTO `sbz_shema`.`bill` (`BILL_ID`, `USR_ID`, `BILL_DATE`, `BILL_STATE`) VALUES ('2', '2', CURRENT_TIMESTAMP(), 'S');
INSERT INTO `sbz_shema`.`bill` (`BILL_ID`, `USR_ID`, `BILL_DATE`, `BILL_STATE`) VALUES ('3', '3', CURRENT_TIMESTAMP(), 'C');

/*BILL ITEM*/
INSERT INTO `sbz_shema`.`bill_item` (`BILL_ID`, `BILLIT_NO`, `IT_ID`, `BILLIT_PRICE`, `BILLIT_QUANTITY`, `BILLIT_ORTOTAL`, `BILLIT_DISCPERC`, `BILLIT_TOTAL`) VALUES ('1', '1', '1', '2500', '2', '5000', '5', '4750');
INSERT INTO `sbz_shema`.`bill_item` (`BILL_ID`, `BILLIT_NO`, `IT_ID`, `BILLIT_PRICE`, `BILLIT_QUANTITY`, `BILLIT_ORTOTAL`, `BILLIT_TOTAL`) VALUES ('1', '2', '2', '5000', '1', '5000', '5000');
INSERT INTO `sbz_shema`.`bill_item` (`BILL_ID`, `BILLIT_NO`, `IT_ID`, `BILLIT_PRICE`, `BILLIT_QUANTITY`, `BILLIT_ORTOTAL`, `BILLIT_DISCPERC`, `BILLIT_TOTAL`) VALUES ('2', '1', '5', '1200', '2', '2400', '10', '2160');
INSERT INTO `sbz_shema`.`bill_item` (`BILL_ID`, `BILLIT_NO`, `IT_ID`, `BILLIT_PRICE`, `BILLIT_QUANTITY`, `BILLIT_ORTOTAL`, `BILLIT_DISCPERC`, `BILLIT_TOTAL`) VALUES ('3', '1', '4', '6000', '2', '12000', '10', '10800');

/*BILL UPDATE*/
UPDATE `sbz_shema`.`bill` SET `BILL_ORTOTAL`='9750', `BILL_TOTAL`='9750', `BILL_APOINTS`='9' WHERE `BILL_ID`='1';
UPDATE `sbz_shema`.`bill` SET `BILL_ORTOTAL`='2160', `BILL_DISCPERC`='10', `BILL_TOTAL`='1900', `BILL_SPOINTS`='10', `BILL_APOINTS`='1' WHERE `BILL_ID`='2';
UPDATE `sbz_shema`.`bill` SET `BILL_ORTOTAL`='10800', `BILL_TOTAL`='10800', `BILL_APOINTS`='10' WHERE `BILL_ID`='3';

/*BILL ITEM DISCOUNT */
INSERT INTO `sbz_shema`.`bill_item_discount` (`BITD_ID`, `BILL_ID`, `BILLIT_NO`, `BITD_DISCOUNT`, `BITD_TYPE`) VALUES ('1', '1', '1', '5', 'R');
INSERT INTO `sbz_shema`.`bill_item_discount` (`BITD_ID`, `BILL_ID`, `BILLIT_NO`, `BITD_DISCOUNT`, `BITD_TYPE`) VALUES ('2', '2', '1', '10', 'R');
INSERT INTO `sbz_shema`.`bill_item_discount` (`BITD_ID`, `BILL_ID`, `BILLIT_NO`, `BITD_DISCOUNT`, `BITD_TYPE`) VALUES ('3', '3', '1', '10', 'A');

/*BILL DISCOUNT*/
INSERT INTO `sbz_shema`.`bill_discount` (`BID_ID`, `BILL_ID`, `BID_DISCOUNT`, `BID_TYPE`) VALUES ('1', '2', '10', 'A');
