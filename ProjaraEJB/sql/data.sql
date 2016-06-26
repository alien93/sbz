/* Customer Category */
INSERT INTO `webshop_sbz`.`customer_category` (`CAT_ID`, `CAT_NAME`) VALUES ('A', 'Zlatni');
INSERT INTO `webshop_sbz`.`customer_category` (`CAT_ID`, `CAT_NAME`) VALUES ('B', 'Srebrni');
INSERT INTO `webshop_sbz`.`customer_category` (`CAT_ID`, `CAT_NAME`) VALUES ('C', 'Over 9000');
INSERT INTO `webshop_sbz`.`customer_category` (`CAT_ID`, `CAT_NAME`) VALUES ('D', 'Platinum');

/*Threshold*/
INSERT INTO `webshop_sbz`.`threshold` (`THRES_FROM`, `THRES_TO`, `THRES_PERCENT`) VALUES ('10000', '20000', '1.0');
INSERT INTO `webshop_sbz`.`threshold` (`THRES_FROM`, `THRES_TO`, `THRES_PERCENT`) VALUES ('30000', '50000', '2.0');
INSERT INTO `webshop_sbz`.`threshold` (`THRES_FROM`, `THRES_TO`, `THRES_PERCENT`) VALUES ('0', '1000', '1');
INSERT INTO `webshop_sbz`.`threshold` (`THRES_FROM`, `THRES_TO`, `THRES_PERCENT`) VALUES ('100000', '999999', '3.0');

/*has_threshold*/
INSERT INTO `webshop_sbz`.`has_thresholds` (`THRES_ID`, `CAT_ID`) VALUES ('1', 'A');
INSERT INTO `webshop_sbz`.`has_thresholds` (`THRES_ID`, `CAT_ID`) VALUES ('2', 'A');
INSERT INTO `webshop_sbz`.`has_thresholds` (`THRES_ID`, `CAT_ID`) VALUES ('3', 'B');
INSERT INTO `webshop_sbz`.`has_thresholds` (`THRES_ID`, `CAT_ID`) VALUES ('4', 'A');
INSERT INTO `webshop_sbz`.`has_thresholds` (`THRES_ID`, `CAT_ID`) VALUES ('4', 'C');
INSERT INTO `webshop_sbz`.`has_thresholds` (`THRES_ID`, `CAT_ID`) VALUES ('2', 'D');

/*users*/
INSERT INTO `webshop_sbz`.`user` (`USR_USERNAME`, `USR_FNAME`, `USR_LNAME`, `USR_REGDAT`, `USR_ROLE`, `USR_PASSWORD`) VALUES ('prodavac', 'Prodavac', 'Prodavčić', '2013-06-15', 'V', '123456');
INSERT INTO `webshop_sbz`.`user` (`USR_USERNAME`, `USR_FNAME`, `USR_LNAME`, `USR_REGDAT`, `USR_ROLE`, `USR_PASSWORD`) VALUES ('menadzer', 'Menadzerko', 'Menadzerković', '2013-05-20', 'M', '123456');
INSERT INTO `webshop_sbz`.`user` (`CAT_ID`, `USR_USERNAME`, `USR_FNAME`, `USR_LNAME`, `USR_REGDAT`, `USR_ROLE`, `USR_CUS_ADDRESS`, `USR_CUS_POINT`, `USR_CUS_RESERVED`, `USR_PASSWORD`) VALUES ('A', 'pera', 'Pera', 'Perić', '2014-01-02', 'C', 'Neka adresa', '230', '0', '123456');
INSERT INTO `webshop_sbz`.`user` (`CAT_ID`, `USR_USERNAME`, `USR_FNAME`, `USR_LNAME`, `USR_REGDAT`, `USR_ROLE`, `USR_CUS_ADDRESS`, `USR_CUS_POINT`, `USR_CUS_RESERVED`, `USR_PASSWORD`) VALUES ('B', 'mika', 'Mika', 'Mikić', '2015-08-24', 'C', 'Adresa Mike', '10', '0', '123456');
INSERT INTO `webshop_sbz`.`user` (`CAT_ID`, `USR_USERNAME`, `USR_FNAME`, `USR_LNAME`, `USR_REGDAT`, `USR_ROLE`, `USR_CUS_ADDRESS`, `USR_CUS_POINT`, `USR_CUS_RESERVED`, `USR_PASSWORD`) VALUES ('C', 'sima', 'Sima', 'Simić', '2013-08-08', 'C', 'Simina Adresa', '1000', '0', '123456');
INSERT INTO `webshop_sbz`.`user` (`CAT_ID`, `USR_USERNAME`, `USR_FNAME`, `USR_LNAME`, `USR_REGDAT`, `USR_ROLE`, `USR_CUS_POINT`, `USR_CUS_RESERVED`, `USR_PASSWORD`) VALUES ('A', 'imenko', 'Imenko', 'Prezimić', '2016-01-01', 'C', '0', '0', '123456');

/*Item category*/
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('A', 'Siroka potrosnja', '20.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('AAA', 'A', 'Prehrambeni proizvodi', '10.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('AAB', 'A', 'Kozmetika', '15.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('AAC', 'A', 'Kućna hemija', '50.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('AAD', 'A', 'Mali kućni aparati', '60.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('ABA', 'AAA', 'Slatkisi', '80.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('B', 'Novogodisnji ukrasi i jelke', '30.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('C', 'Oprema za kampovanje', '50.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('CCC', 'C', 'Oprema za rostilj', '30.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('D', 'Televizori, racunari, laptopovi', '40.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('DDD', 'D', 'Televizori', '30.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('DDA', 'D', 'Racunari', '60.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('DDB', 'D', 'Laptopovi', '30.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('DAA', 'DDA', 'Racunarske komponente', '25.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('E', 'Alkoholna pica', '10.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('EEA', 'E', 'Domaca alkoholna pica', '15.0');
INSERT INTO `webshop_sbz`.`item_category` (`ITCAT_CODE`, `ITE_ITCAT_CODE`, `ITCAT_NAME`, `ITCAT_MDISCOUNT`) VALUES ('EEE', 'E', 'Strana alkoholna pica', '5.0');

/*action events*/
INSERT INTO `webshop_sbz`.`action_event` (`AE_NAME`, `AE_FROM`, `AE_UNTIL`, `AE_DISCOUNT`) VALUES ('Nova Godina 2015', '2014-12-15', '2015-01-05', '8.0');
INSERT INTO `webshop_sbz`.`action_event` (`AE_NAME`, `AE_FROM`, `AE_UNTIL`, `AE_DISCOUNT`) VALUES ('Varljivo leto 2015', '2015-06-20', '2015-09-01', '20.0');
INSERT INTO `webshop_sbz`.`action_event` (`AE_NAME`, `AE_FROM`, `AE_UNTIL`, `AE_DISCOUNT`) VALUES ('Leto 2016', '2016-06-20', '2016-08-20', '10.0');
INSERT INTO `webshop_sbz`.`action_event` (`AE_NAME`, `AE_FROM`, `AE_UNTIL`, `AE_DISCOUNT`) VALUES ('Popust na tehniku', '2016-06-25', '2016-07-10', '5.0');

/*on discount*/
INSERT INTO `webshop_sbz`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('1', 'A');
INSERT INTO `webshop_sbz`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('1', 'E');
INSERT INTO `webshop_sbz`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('1', 'B');
INSERT INTO `webshop_sbz`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('2', 'AAA');
INSERT INTO `webshop_sbz`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('2', 'C');
INSERT INTO `webshop_sbz`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('3', 'AAA');
INSERT INTO `webshop_sbz`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('3', 'C');
INSERT INTO `webshop_sbz`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('3', 'EEE');
INSERT INTO `webshop_sbz`.`on_discount` (`AE_ID`, `ITCAT_CODE`) VALUES ('4', 'D');

/*items*/
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('AAA', 'Pasteta (haha)', '200.00', '50', '2011-03-05', '0', '1', '10', 'pasteta.jpg');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('AAA', 'Cips 1kg', '300.00', '100', '2012-03-05', '0', '1', '20', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('AAB', 'STR8 Oxygen', '250.00', '150', '2012-03-05', '0', '1', '20', 'str8oxy.jpg');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('AAB', 'Pena za brijanje', '200.00', '40', '2012-03-05', '0', '1', '50', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('AAC', 'Domestos i milioni bakterija ce umreti', '300.00', '200', '2012-03-05', '0', '1', '30', 'domestos.jpg');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('AAD', 'Mikser', '2500.00', '20', '2012-03-05', '0', '1', '5', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('AAD', 'Pegla', '2300.00', '100', '2012-03-05', '0', '1', '10', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('AAD', 'Mikrotalasna', '5000.00', '200', '2012-03-05', '0', '1', '30', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('ABA', 'Haribo gumene mede', '200.00', '300', '2012-03-05', '0', '1', '100', 'haribo.jpg');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('ABA', 'Cokolada 300g', '250.00', '300', '2012-03-05', '0', '1', '100', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('ABA', 'Lizalica', '100.00', '200', '2012-03-05', '0', '1', '50', 'lizalica.jpg');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('B', 'Jelka 2m', '500.00', '300', '2012-03-05', '0', '1', '100', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('B', 'Stakleni ukrasi 5kom', '1000.00', '100', '2012-03-05', '0', '1', '20', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('B', 'Plasticni ukrasi 5kom', '100.00', '500', '2012-03-05', '0', '1', '100', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('B', 'Svecice 5m', '300.00', '200', '2012-03-05', '0', '1', '50', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('C', 'Sator', '5000.00', '200', '2012-03-05', '0', '1', '50', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('C', 'Vreca za spavanje', '3000.00', '200', '2012-03-05', '0', '1', '50', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('CCC', 'Rostilj', '4000.00', '100', '2012-03-05', '0', '1', '150', 'rostilj.jpg');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('CCC', 'Cumur 1kg', '200.00', '500', '2012-03-05', '0', '1', '100', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('D', 'Sony playstation 4', '51000.00', '20', '2012-03-05', '0', '1', '10', 'ps4.jpg');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('DDD', 'LG televizor', '80000.00', '30', '2012-03-05', '0', '1', '20', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('DDD', 'Samsung televizor', '100000.00', '10', '2012-03-05', '0', '1', '5', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('DDA', 'PC racunar dobar', '120000.00', '10', '2012-03-05', '0', '1', '5', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('DDA', 'PC racunar los', '20000.00', '30', '2012-03-05', '0', '1', '15', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('DDB', 'Laptop jado', '15000.00', '20', '2012-03-05', '0', '1', '5', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('DDB', 'Laptop Over 9000', '130000.00', '10', '2012-03-05', '0', '1', '5', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('DAA', 'Graficka GTX 1080', '120000.00', '5', '2012-03-05', '0', '1', '2', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('DAA', 'Intel i5 3200', '35000.00', '15', '2012-03-05', '0', '1', '10', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('E', 'Etil alkohol 1l', '200.00', '50', '2012-03-05', '0', '1', '20', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('EEE', 'Vodka', '2000.00', '10', '2012-03-05', '0', '1', '10', 'defaultImage.png');
INSERT INTO `webshop_sbz`.`item` (`ITCAT_CODE`, `IT_NAME`, `IT_PRICE`, `IT_INSTOCK`, `IT_CREATEDON`, `IT_ISLOW`, `IT_RECSTATE`, `IT_MINQUANT`, `IT_PIC`) VALUES ('EEA', 'Jelen pivo 10l', '800.00', '50', '2012-03-05', '0', '1', '30', 'defaultImage.png');
